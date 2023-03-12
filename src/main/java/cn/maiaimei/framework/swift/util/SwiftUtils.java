package cn.maiaimei.framework.swift.util;

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
        List<Field> declaredFields = ReflectionUtils.getSelfDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            populateField(block, message, declaredField);
        }
    }

    @SneakyThrows
    public static void populateMessage(SwiftTagListBlock block, Map<String, List<SwiftTagListBlock>> sequenceMap, BaseMessage message) {
        Class<? extends BaseMessage> clazz = message.getClass();
        List<Field> declaredFields = ReflectionUtils.getSelfDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            if (BaseSequence.class.isAssignableFrom(declaredField.getType())) {
                Sequence sequenceAnnotation = declaredField.getAnnotation(Sequence.class);
                List<SwiftTagListBlock> blocks = sequenceMap.get(sequenceAnnotation.value());
                declaredField.setAccessible(Boolean.TRUE);
                Object seqObj = declaredField.get(message);
                List<Field> sequenceDeclaredFields = ReflectionUtils.getSelfDeclaredFields(declaredField.getType());
                for (Field sequenceDeclaredField : sequenceDeclaredFields) {
                    populateField(blocks.get(sequenceAnnotation.index()), seqObj, sequenceDeclaredField);
                }
                declaredField.setAccessible(Boolean.FALSE);
            } else {
                populateField(block, message, declaredField);
            }
        }
    }

    @SneakyThrows
    private static void populateField(SwiftTagListBlock block, Object target, Field declaredField) {
        Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
        com.prowidesoftware.swift.model.field.Field field = block.getFieldByName(tagAnnotation.value());
        if (field == null) {
            return;
        }
        declaredField.setAccessible(Boolean.TRUE);
        if (BeanUtils.isSimpleProperty(declaredField.getType())) {
            declaredField.set(target, field.getValue());
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
