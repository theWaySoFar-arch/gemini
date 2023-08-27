package org.gemini.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe String工具类
 * @date 2023/8/27 13:30
 */
public class StringUtils {

    private StringUtils() {
            // 私有构造函数，防止类实例化
    }

    /**
     * 是否为空字符串
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 将String类型转为Map类型
     * @param str
     * @return
     */
    public static Map<String, String> stringToMap(String str) {
        Map<String, String> map = new HashMap<>();
            if (!isEmpty(str)) {
                String[] keyValuePairs = str.split(",");
                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        map.put(keyValue[0], keyValue[1]);
                    }
                }
            }
            return map;
        }
 }
