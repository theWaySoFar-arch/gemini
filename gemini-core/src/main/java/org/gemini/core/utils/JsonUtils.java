package org.gemini.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe Json工具类
 * @date 2023/8/25 20:05
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 序列化
     * @param object
     * @return
     */
    public static String serialize(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反序列化
     * @param json
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T deserialize(final String json, final Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反序列化List类型数据
     * @param json
     * @param elementType
     * @param <T>
     * @return
     */
    public static <T> List<T> deserializeList(final String json,final Class<T> elementType) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反序列化Map类型数据
     * @param json
     * @param keyType
     * @param valueType
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> deserializeMap(final String json, final Class<K> keyType, Class<V> valueType) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
