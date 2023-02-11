package cn.maiaimei.framework.swift.util;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.BaseSequence;
import cn.maiaimei.framework.swift.validation.constants.MTClassNameUtils;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class SwiftUtils {

    private static final String GET_SEQUENCE_METHOD_NAME = "getSequence";

    @SneakyThrows
    public static void populateMessage(SwiftTagListBlock block, BaseMessage message) {
        Class<? extends BaseMessage> clazz = message.getClass();
        List<Field> declaredFields = getDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            doPopulateMessage(block, message, declaredField);
        }
    }

    @SneakyThrows
    public static void populateMessage(SwiftTagListBlock block, Map<String, List<SwiftTagListBlock>> sequenceMap, BaseMessage message) {
        Class<? extends BaseMessage> clazz = message.getClass();
        List<Field> declaredFields = getDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            if (BaseSequence.class.isAssignableFrom(declaredField.getType())) {
                Sequence sequenceAnnotation = declaredField.getAnnotation(Sequence.class);
                List<SwiftTagListBlock> blocks = sequenceMap.get(sequenceAnnotation.value());
                declaredField.setAccessible(Boolean.TRUE);
                Object seqObj = declaredField.get(message);
                List<Field> sequenceDeclaredFields = getDeclaredFields(declaredField.getType());
                for (Field sequenceDeclaredField : sequenceDeclaredFields) {
                    // TODO: handle multiple sequence block
                    doPopulateMessage(blocks.get(0), seqObj, sequenceDeclaredField);
                }
                declaredField.setAccessible(Boolean.FALSE);
            } else {
                doPopulateMessage(block, message, declaredField);
            }
        }
    }

    @SneakyThrows
    private static void doPopulateMessage(SwiftTagListBlock block, Object obj, Field declaredField) {
        Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
        com.prowidesoftware.swift.model.field.Field field = block.getFieldByName(tagAnnotation.value());
        if (field == null) {
            String[] tags = tagAnnotation.tags();
            for (String tag : tags) {
                field = block.getFieldByName(tag);
                if (field != null) {
                    break;
                }
            }
        }
        if (field == null) {
            return;
        }
        declaredField.setAccessible(Boolean.TRUE);
        declaredField.set(obj, tagAnnotation.index() == -1 ? field.getValue() : field.getComponent(tagAnnotation.index()));
        declaredField.setAccessible(Boolean.FALSE);
    }

    private static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @SneakyThrows
    public static SwiftMessage parseToSwiftMessage(String message) {
        SwiftParser parser = new SwiftParser(message);
        return parser.message();
    }

    public static AbstractMT parseToAbstractMT(String message, String messageType) {
        SwiftMessage swiftMessage = parseToSwiftMessage(message);
        return new AbstractMT(swiftMessage) {
            @Override
            public String getMessageType() {
                return messageType;
            }
        };
    }

    public static AbstractMT parseToAbstractMT(SwiftMessage swiftMessage, String messageType) {
        return new AbstractMT(swiftMessage) {
            @Override
            public String getMessageType() {
                return messageType;
            }
        };
    }

    public static SwiftBlock4 getBlock4(String message) {
        SwiftMessage swiftMessage = parseToSwiftMessage(message);
        return swiftMessage.getBlock4();
    }

    @Deprecated
    @SneakyThrows
    public static Map<String, List<SwiftTagListBlock>> getSequenceMap(String messageType, SwiftTagListBlock block4) {
        Map<String, List<SwiftTagListBlock>> sequenceMap = new HashMap<>();
        String className = MTClassNameUtils.MAP.get(messageType);
        if (StringUtils.isBlank(className)) {
            throw new ClassNotFoundException("Can't found class for MT" + messageType);
        }
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.newInstance();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String declaredMethodName = declaredMethod.getName();
            if (!declaredMethodName.startsWith(GET_SEQUENCE_METHOD_NAME)) {
                continue;
            }
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            if (parameterTypes.length == 1
                    && SwiftTagListBlock.class.getSimpleName().equals(parameterTypes[0].getSimpleName())) {
                Object result = declaredMethod.invoke(instance, block4);
                List<SwiftTagListBlock> blockList;
                if (result instanceof List) {
                    blockList = (List<SwiftTagListBlock>) result;
                } else {
                    blockList = Collections.singletonList((SwiftTagListBlock) result);
                }
                if (CollectionUtils.isEmpty(blockList)) {
                    blockList = Collections.emptyList();
                }
                sequenceMap.put(declaredMethodName.replaceAll(GET_SEQUENCE_METHOD_NAME, StringUtils.EMPTY), blockList);
            }
        }
        return sequenceMap;
    }

    private SwiftUtils() {
        throw new UnsupportedOperationException();
    }
}
