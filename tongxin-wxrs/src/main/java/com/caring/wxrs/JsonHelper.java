package com.caring.wxrs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author james
 */
public class JsonHelper {

    public static String toJSON(Object o) {
        return JSON.toJSONString(o, SerializerFeature.WriteMapNullValue);
    }

    public static SerializeWriter toJSON(Object obj, final Map<Class<?>, Set<String>> excludeMap) {
        SerializeWriter serializerResultWriter = new SerializeWriter();
        serializerResultWriter.config(SerializerFeature.DisableCircularReferenceDetect, true);
        JSONSerializer serializer = new JSONSerializer(serializerResultWriter);
        serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
        serializer.getPropertyFilters().add((Object source, String name, Object value) -> {
            for (Entry<Class<?>, Set<String>> entry : excludeMap.entrySet()) {
                Class<?> clazz = entry.getKey();
                if (source.getClass().equals(clazz)) {
                    Set<String> fields = entry.getValue();
                    if (fields.contains(name)) {
                        return false;
                    }
                }
            }
            return true;
        });
        serializer.write(obj);
        return serializerResultWriter;
    }

    static Object toJSON(Object obj, Map<Class<?>, Set<String>> includeMap, Map<Class<?>, Set<String>> excludeMap) {
        SerializeWriter serializerResultWriter = new SerializeWriter();
        serializerResultWriter.config(SerializerFeature.DisableCircularReferenceDetect, true);
        JSONSerializer serializer = new JSONSerializer(serializerResultWriter);
        serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
        if (!excludeMap.isEmpty()) {
            serializer.getPropertyFilters().add((Object source, String name, Object value) -> {
                for (Entry<Class<?>, Set<String>> entry : excludeMap.entrySet()) {
                    Set<String> fields = entry.getValue();
                    if (fields.isEmpty()) {
                        return true;
                    }
                    Class<?> clazz = entry.getKey();
                    if (source.getClass().equals(clazz)) {
                        if (fields.contains(name)) {
                            return false;
                        }
                    }
                }
                return true;
            });
        }
        if (!includeMap.isEmpty()) {
            serializer.getPropertyFilters().add((Object source, String name, Object value) -> {
                for (Entry<Class<?>, Set<String>> entry : includeMap.entrySet()) {
                    Set<String> fields = entry.getValue();
                    if (fields.isEmpty()) {
                        return true;
                    }
                    Class<?> clazz = entry.getKey();
                    if (source.getClass().equals(clazz)) {
                        if (fields.contains(name)) {
                            return true;
                        }
                        return false;
                    }
                }
                return true;
            });
        }
        serializer.write(obj);
        return serializerResultWriter;
    }

}
