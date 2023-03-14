package cn.maiaimei.framework.swift.util;

import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798BaseMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798ExtensionMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static final String GET_INDEX_MESSAGE_METHOD_NAME = "getIndexMessage";
    public static final String GET_DETAIL_MESSAGES_METHOD_NAME = "getDetailMessages";
    public static final String GET_EXTENSION_MESSAGES_METHOD_NAME = "getExtensionMessages";
    public static final String SET_INDEX_MESSAGE_METHOD_NAME = "setIndexMessage";
    public static final String SET_DETAIL_MESSAGES_METHOD_NAME = "setDetailMessages";
    public static final String SET_EXTENSION_MESSAGES_METHOD_NAME = "setExtensionMessages";

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

    public static Method obtainGetIndexMessageMethod(Method[] declaredMethods) {
        return obtainNoArgsMethod(declaredMethods, GET_INDEX_MESSAGE_METHOD_NAME);
    }

    public static Method obtainGetDetailMessagesMethod(Method[] declaredMethods) {
        return obtainNoArgsMethod(declaredMethods, GET_DETAIL_MESSAGES_METHOD_NAME);
    }

    public static Method obtainGetExtensionMessagesMethod(Method[] declaredMethods) {
        return obtainNoArgsMethod(declaredMethods, GET_EXTENSION_MESSAGES_METHOD_NAME);
    }

    public static Method obtainSetIndexMessageMethod(Method[] declaredMethods) {
        for (Method declaredMethod : declaredMethods) {
            if (SET_INDEX_MESSAGE_METHOD_NAME.equals(declaredMethod.getName())
                    && MT798IndexMessage.class.isAssignableFrom(declaredMethod.getParameterTypes()[0])) {
                return declaredMethod;
            }
        }
        return null;
    }

    public static Method obtainSetDetailMessagesMethod(Method[] declaredMethods) {
        return obtainMethod(declaredMethods, SET_DETAIL_MESSAGES_METHOD_NAME, MT798DetailMessage.class);
    }

    public static Method obtainSetExtensionMessagesMethod(Method[] declaredMethods) {
        return obtainMethod(declaredMethods, SET_EXTENSION_MESSAGES_METHOD_NAME, MT798ExtensionMessage.class);
    }

    private static Method obtainNoArgsMethod(Method[] declaredMethods, String methodName) {
        for (Method declaredMethod : declaredMethods) {
            if (methodName.equals(declaredMethod.getName())
                    && declaredMethod.getParameterTypes().length == 0) {
                return declaredMethod;
            }
        }
        return null;
    }

    private static <T extends MT798BaseMessage> Method obtainMethod(Method[] declaredMethods, String methodName, Class<T> actualType) {
        for (Method declaredMethod : declaredMethods) {
            if (!methodName.equals(declaredMethod.getName())) {
                continue;
            }
            Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
            Type genericParameterType = genericParameterTypes[0];
            if (genericParameterType instanceof ParameterizedTypeImpl) {
                ParameterizedTypeImpl type = (ParameterizedTypeImpl) genericParameterType;
                if (List.class.isAssignableFrom(type.getRawType())
                        && actualType.isAssignableFrom((Class<?>) type.getActualTypeArguments()[0])) {
                    return declaredMethod;
                }
            }
        }
        return null;
    }

    public ReflectionUtils() {
        throw new UnsupportedOperationException();
    }
}
