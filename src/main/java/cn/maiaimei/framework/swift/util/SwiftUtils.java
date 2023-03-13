package cn.maiaimei.framework.swift.util;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.annotation.WithSequence;
import cn.maiaimei.framework.swift.exception.ProcessorNotFoundException;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.BaseSequence;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798BaseMessage;
import cn.maiaimei.framework.swift.processor.MessageSequenceProcessor;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SwiftUtils {

    @Autowired
    private Set<MessageSequenceProcessor> sequenceProcessorSet;

    /**
     * convert BaseMessage to AbstractMT
     */
    @SneakyThrows
    public void convert(BaseMessage baseMessage, AbstractMT mt) {
        List<Field> selfFields = ReflectionUtils.getSelfDeclaredFields(baseMessage.getClass());
        List<Field> superclassDeclaredFields = ReflectionUtils.getSuperclassDeclaredFields(baseMessage.getClass(), MT798BaseMessage.class);
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(superclassDeclaredFields);
        allFields.addAll(selfFields);
        for (Field declaredField : allFields) {
            declaredField.setAccessible(Boolean.TRUE);
            Object value = declaredField.get(baseMessage);
            Sequence sequenceAnnotation = declaredField.getAnnotation(Sequence.class);
            if (sequenceAnnotation != null && value != null) {
                List<Field> sequenceFields = ReflectionUtils.getSelfDeclaredFields(value.getClass());
                for (Field sequenceField : sequenceFields) {
                    sequenceField.setAccessible(Boolean.TRUE);
                    Tag tagAnno = sequenceField.getAnnotation(Tag.class);
                    Object val = sequenceField.get(value);
                    if (tagAnno != null && val != null) {
                        mt.append(new com.prowidesoftware.swift.model.Tag(tagAnno.value(), val.toString()));
                    }
                    sequenceField.setAccessible(Boolean.FALSE);
                }
            }
            Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
            if (tagAnnotation != null && value != null) {
                mt.append(new com.prowidesoftware.swift.model.Tag(tagAnnotation.value(), value.toString()));
            }
            declaredField.setAccessible(Boolean.FALSE);
        }
    }

    /**
     * populate BaseMessage from AbstractMT
     */
    public void populate(BaseMessage message, AbstractMT mt) {
        SwiftBlock4 block = mt.getSwiftMessage().getBlock4();
        if (message.getClass().isAnnotationPresent(WithSequence.class)) {
            MessageSequenceProcessor sequenceProcessor = getSequenceProcessor(mt.getMessageType());
            Map<String, List<SwiftTagListBlock>> sequenceMap = sequenceProcessor.getSequenceMap(mt);
            populateMessage(message, block, sequenceMap);
        } else {
            populateMessage(message, block);
        }
    }

    /**
     * populate BaseMessage from SwiftTagListBlock
     */
    @SneakyThrows
    public void populateMessage(BaseMessage message, SwiftTagListBlock block) {
        Class<? extends BaseMessage> clazz = message.getClass();
        List<Field> declaredFields = ReflectionUtils.getSelfDeclaredFields(clazz);
        for (Field declaredField : declaredFields) {
            populateField(block, message, declaredField);
        }
    }

    /**
     * populate BaseMessage from SwiftTagListBlock and Map<String, List<SwiftTagListBlock>>
     */
    @SneakyThrows
    public void populateMessage(BaseMessage message, SwiftTagListBlock block, Map<String, List<SwiftTagListBlock>> sequenceMap) {
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
    private void populateField(SwiftTagListBlock block, Object target, Field declaredField) {
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

    private MessageSequenceProcessor getSequenceProcessor(String messageType) {
        for (MessageSequenceProcessor processor : sequenceProcessorSet) {
            if (processor.supportsMessageType(messageType)) {
                return processor;
            }
        }
        throw new ProcessorNotFoundException("Can't found MessageSequenceProcessor for MT" + messageType);
    }
}
