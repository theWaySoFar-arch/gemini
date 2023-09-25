package org.gemini.server.doris;


import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class StreamLoadTask {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(StreamLoadTask.class);
    @Value("${apache.doris.host}")
    private   String DORIS_HOST ;
    @Value("${apache.doris.db}")
    private   String DORIS_DB ;
    @Value("${apache.doris.table}")
    private   String DORIS_TABLE ;
    @Value("${apache.doris.user}")
    private   String DORIS_USER ;
    @Value("${apache.doris.password}")
    private   String DORIS_PASSWORD ;
    @Value("${apache.doris.http-port}")
    private   int DORIS_HTTP_PORT ;

    public void send(List<String> list){
        StringBuilder stringBuilder=new StringBuilder();
        for(String str:list){
            stringBuilder.append(str).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        try {
            sendData(stringBuilder.toString());
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }
    private void sendData(final String content) throws Exception {
        final String loadUrl = String.format("http://%s:%s/api/%s/%s/_stream_load",
                DORIS_HOST,
                DORIS_HTTP_PORT,
                DORIS_DB,
                DORIS_TABLE);

        final HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .setRedirectStrategy(new DefaultRedirectStrategy() {
                    @Override
                    protected boolean isRedirectable(String method) {
                        return true;
                    }
                });

        try (CloseableHttpClient client = httpClientBuilder.build()) {
            HttpPut put = new HttpPut(loadUrl);
            StringEntity entity = new StringEntity(content, "UTF-8");
            put.setHeader(HttpHeaders.EXPECT, "100-continue");
            put.setHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(DORIS_USER, DORIS_PASSWORD));
            // the label header is optional, not necessary
            // use label header can ensure at most once semantics
            put.setHeader("label", "39c25a5c-7000-496e-a98e-348a264c81de");
            put.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(put)) {
                String loadResult = "";
                if (response.getEntity() != null) {
                    loadResult = EntityUtils.toString(response.getEntity());
                }
                final int statusCode = response.getStatusLine().getStatusCode();
                // statusCode 200 just indicates that doris be service is ok, not stream load
                // you should see the output content to find whether stream load is success
                if (statusCode != 200) {
                    throw new IOException(
                            String.format("Stream load failed, statusCode=%s load result=%s", statusCode, loadResult));
                }

                System.out.println(loadResult);
            }
        }
    }
    // 创建 PUT request
/*    private Request buildRequest(final String loadUrl) {
        return   new Request.Builder()
                .url(loadUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader(HttpHeaders.EXPECT, "100-continue")
                .addHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader(DORIS_USER, DORIS_PASSWORD))
                .addHeader("label", UUID.randomUUID().toString())
                .addHeader("format", "json")
                .addHeader("read_json_by_line", "true")
                .build();
    }*/

    private String basicAuthHeader(String username, String password) {
        final String tobeEncode = username + ":" + password;
        byte[] encoded = Base64.encodeBase64(tobeEncode.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encoded);
    }


}
