package cn.maiaimei.framework.swift.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    /**
     * get declared fields for current class and super class
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * get declared fields for super class
     */
    public static List<Field> getSuperclassDeclaredFields(Class<?> clazz, Class<?> superclass) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            if (superclass == clazz) {
                Field[] declaredFields = clazz.getDeclaredFields();
                fields.addAll(Arrays.asList(declaredFields));
                break;
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * get declared fields for current class
     */
    public static List<Field> getSelfDeclaredFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    public ReflectionUtils() {
        throw new UnsupportedOperationException();
    }
}
