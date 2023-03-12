package cn.maiaimei.framework.swift.util;

import cn.maiaimei.framework.swift.annotation.Component;
import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.BaseSequence;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SwiftUtils {

    @SneakyThrows
    public static void populateMessage(SwiftTagListBlock block, BaseMessage message) {
        Class<? extends BaseMessage> clazz = message.getClass();
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            populateField(block, message, declaredField);
        }
    }

    @SneakyThrows
    public static void populateMessage(SwiftTagListBlock block, Map<String, List<SwiftTagListBlock>> sequenceMap, BaseMessage message) {
        Class<? extends BaseMessage> clazz = message.getClass();
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            if (BaseSequence.class.isAssignableFrom(declaredField.getType())) {
                Sequence sequenceAnnotation = declaredField.getAnnotation(Sequence.class);
                List<SwiftTagListBlock> blocks = sequenceMap.get(sequenceAnnotation.value());
                declaredField.setAccessible(Boolean.TRUE);
                Object seqObj = declaredField.get(message);
                List<Field> sequenceDeclaredFields = ReflectionUtils.getDeclaredFields(declaredField.getType());
                for (Field sequenceDeclaredField : sequenceDeclaredFields) {
                    populateField(blocks.get(sequenceAnnotation.index()), seqObj, sequenceDeclaredField);
                }
                declaredField.setAccessible(Boolean.FALSE);
            } else {
                populateField(block, message, declaredField);
            }
        }
    }


    private static void populateField(SwiftTagListBlock block, Object target, Field declaredField) {
        Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
        com.prowidesoftware.swift.model.field.Field field;
        if (tagAnnotation.tags().length == 0) {
            field = block.getFieldByName(tagAnnotation.value());
            doPopulateField(target, declaredField, field);
            return;
        }
        for (String tag : tagAnnotation.tags()) {
            field = block.getFieldByName(tag);
            doPopulateField(target, declaredField, field);
        }
    }

    @SneakyThrows
    private static void doPopulateField(Object target, Field declaredField, com.prowidesoftware.swift.model.field.Field field) {
        if (field == null) {
            return;
        }
        declaredField.setAccessible(Boolean.TRUE);
        if (BeanUtils.isSimpleProperty(declaredField.getType())) {
            declaredField.set(target, field.getValue());
        } else {
            Object property = declaredField.get(target) != null ? declaredField.get(target) : declaredField.getType().newInstance();
            List<Field> propertyDeclaredFields = ReflectionUtils.getDeclaredFields(declaredField.getType());
            for (Field propertyDeclaredField : propertyDeclaredFields) {
                Tag tagAnnotation = propertyDeclaredField.getAnnotation(Tag.class);
                if ((tagAnnotation != null && tagAnnotation.value().equals(field.getName()))) {
                    doPopulateField(property, propertyDeclaredField, field);
                } else if (BeanUtils.isSimpleProperty(propertyDeclaredField.getType())) {
                    Component componentAnnotation = propertyDeclaredField.getAnnotation(Component.class);
                    propertyDeclaredField.setAccessible(Boolean.TRUE);
                    propertyDeclaredField.set(property, componentAnnotation == null ? field.getValue() : field.getComponent(componentAnnotation.index()));
                    propertyDeclaredField.setAccessible(Boolean.FALSE);
                }
            }
            declaredField.set(target, property);
        }
        declaredField.setAccessible(Boolean.FALSE);
    }

    @SneakyThrows
    public static AbstractMT parseToAbstractMT(String message) {
        // Parse unknown MT message into generic swift model
        return AbstractMT.parse(message);
    }

    private SwiftUtils() {
        throw new UnsupportedOperationException();
    }
}
