package cn.maiaimei.framework.swift.util;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.BaseSequence;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public static List<Field> getDeclaredFields(Class<?> clazz) {
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
    
    private SwiftUtils() {
        throw new UnsupportedOperationException();
    }
}
