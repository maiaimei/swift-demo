package cn.maiaimei.framework.swift.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static List<Field> getSelfDeclaredFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        final Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (fieldName.equals(declaredField.getName())) {
                return declaredField;
            }
        }
        return null;
    }

    public ReflectionUtils() {
        throw new UnsupportedOperationException();
    }
}
