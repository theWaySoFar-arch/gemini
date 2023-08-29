package org.gemini.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 实体转换工具类
 * @date 2023/8/29 19:55
 */
public class EntityConvertUtils {
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        try {
            Class clazz = object.getClass();
            List fieldsList = new ArrayList<Field[]>();
            while (clazz != null) {
                Field[] declaredFields = clazz.getDeclaredFields();
                fieldsList.add(declaredFields);
                clazz = clazz.getSuperclass();
            }
            for (Object fields:fieldsList) {
                Field[] f = (Field[]) fields;
                for (Field field : f) {
                    field.setAccessible(true);
                    Object o = field.get(object);
                    map.put(field.getName(), o);
                }
            }
        }catch (Exception e){}

        return map;
    }
}
