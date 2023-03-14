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

    private static final String GET_INDEX_MESSAGE_METHOD_NAME = "getIndexMessage";
    private static final String GET_DETAIL_MESSAGES_METHOD_NAME = "getDetailMessages";
    private static final String GET_EXTENSION_MESSAGES_METHOD_NAME = "getExtensionMessages";
    private static final String SET_INDEX_MESSAGE_METHOD_NAME = "setIndexMessage";
    private static final String SET_DETAIL_MESSAGES_METHOD_NAME = "setDetailMessages";
    private static final String SET_EXTENSION_MESSAGES_METHOD_NAME = "setExtensionMessages";

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
        for (Method declaredMethod : declaredMethods) {
            if (GET_INDEX_MESSAGE_METHOD_NAME.equals(declaredMethod.getName())
                    && declaredMethod.getParameterTypes().length == 0) {
                return declaredMethod;
            }
        }
        return null;
    }

    public static Method obtainGetDetailMessagesMethod(Method[] declaredMethods) {
        for (Method declaredMethod : declaredMethods) {
            if (GET_DETAIL_MESSAGES_METHOD_NAME.equals(declaredMethod.getName())
                    && declaredMethod.getParameterTypes().length == 0) {
                return declaredMethod;
            }
        }
        return null;
    }

    public static Method obtainGetExtensionMessagesMethod(Method[] declaredMethods) {
        for (Method declaredMethod : declaredMethods) {
            if (GET_EXTENSION_MESSAGES_METHOD_NAME.equals(declaredMethod.getName())
                    && declaredMethod.getParameterTypes().length == 0) {
                return declaredMethod;
            }
        }
        return null;
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
        for (Method declaredMethod : declaredMethods) {
            if (checkMethod(declaredMethod, SET_DETAIL_MESSAGES_METHOD_NAME, MT798DetailMessage.class)) {
                return declaredMethod;
            }
        }
        return null;
    }

    public static Method obtainSetExtensionMessagesMethod(Method[] declaredMethods) {
        for (Method declaredMethod : declaredMethods) {
            if (checkMethod(declaredMethod, SET_EXTENSION_MESSAGES_METHOD_NAME, MT798ExtensionMessage.class)) {
                return declaredMethod;
            }
        }
        return null;
    }

    private static <T extends MT798BaseMessage> boolean checkMethod(Method method, String methodName, Class<T> actualType) {
        if (!methodName.equals(method.getName())) {
            return false;
        }
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Type genericParameterType = genericParameterTypes[0];
        if (genericParameterType instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl type = (ParameterizedTypeImpl) genericParameterType;
            return List.class.isAssignableFrom(type.getRawType())
                    && actualType.isAssignableFrom((Class<?>) type.getActualTypeArguments()[0]);
        }
        return false;
    }

    public ReflectionUtils() {
        throw new UnsupportedOperationException();
    }
}
