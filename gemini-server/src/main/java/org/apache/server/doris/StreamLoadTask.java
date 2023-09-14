package org.apache.server.doris;

import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
@Component
public class StreamLoadTask {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(StreamLoadTask.class);
    @Value("${apache.doris.host}")
    private  static String DORIS_HOST ;
    @Value("${apache.doris.db}")
    private  static String DORIS_DB ;
    @Value("${apache.doris.table}")
    private  static String DORIS_TABLE ;
    @Value("${apache.doris.user}")
    private  static String DORIS_USER ;
    @Value("${apache.doris.password}")
    private  static String DORIS_PASSWORD ;
    @Value("${apache.doris.http-port}")
    private  static int DORIS_HTTP_PORT ;
    @Resource
    private OkHttpClient okHttpClient;
    private void sendData(final String content) throws Exception {
        //构建URL
        final String loadUrl = String.format("http://%s:%s/api/%s/%s/_stream_load",
                DORIS_HOST,
                DORIS_HTTP_PORT,
                DORIS_DB,
                DORIS_TABLE);
        Request request = buildRequest(loadUrl);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(content, mediaType);
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // 处理成功响应
                String responseBody = response.body().string();
               logger.info(responseBody);
            } else {
                // 处理错误响应
                logger.error("Error: " + response.code() + " - " + response.message());
            }
        }
    }
    // 创建 PUT request
    private Request buildRequest(final String loadUrl) {
        return   new Request.Builder()
                .url(loadUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader(HttpHeaders.EXPECT, "100-continue")
                .addHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(DORIS_USER, DORIS_PASSWORD))
                .addHeader("label", UUID.randomUUID().toString())
                .addHeader("format", "json")
                .addHeader("read_json_by_line", "true")
                .build();
    }

    private String basicAuthHeader(String username, String password) {
        final String tobeEncode = username + ":" + password;
        byte[] encoded = Base64.encodeBase64(tobeEncode.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encoded);
    }


}
