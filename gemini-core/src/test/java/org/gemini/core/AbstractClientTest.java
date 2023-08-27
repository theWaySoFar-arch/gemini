package org.gemini.core;

import org.gemini.core.client.KafkaConsumerClient;
import org.gemini.core.utils.StringUtils;
import org.junit.Test;

import java.util.Map;

public class AbstractClientTest {
    @Test
    public void StringTest(){
        String str="";
        boolean empty = StringUtils.isEmpty(str);
        System.out.println(empty);
        String map="kebo:bryant,li:lee,cai:xukun";
        Map<String, String> map1 = StringUtils.stringToMap(map);
        for(Map.Entry<String,String>entry:map1.entrySet()){
            System.out.println("key is:"+entry.getKey()+"value is :"+entry.getValue());
        }
    }

}
