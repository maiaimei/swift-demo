package cn.maiaimei.framework.swift.converter;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.annotation.WithSequence;
import cn.maiaimei.framework.swift.exception.ProcessorNotFoundException;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.BaseSequence;
import cn.maiaimei.framework.swift.processor.MessageSequenceProcessor;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MtToMsConverter {

    @Autowired
    private Set<MessageSequenceProcessor> sequenceProcessorSet;

    public void convert(AbstractMT mt, BaseMessage ms) {
        SwiftBlock4 block = mt.getSwiftMessage().getBlock4();
        if (ms.getClass().isAnnotationPresent(WithSequence.class)) {
            String messageType = mt.getMessageType();
            MessageSequenceProcessor sequenceProcessor = getSequenceProcessor(messageType);
            Map<String, List<SwiftTagListBlock>> sequenceMap = sequenceProcessor.getSequenceMap(mt);
            populateMessage(ms, block, sequenceMap);
        } else {
            populateMessage(ms, block);
        }
    }

    public MessageSequenceProcessor getSequenceProcessor(String messageType) {
        for (MessageSequenceProcessor processor : sequenceProcessorSet) {
            if (processor.supportsMessageType(messageType)) {
                return processor;
            }
        }
        throw new ProcessorNotFoundException("Can't found MessageSequenceProcessor for MT" + messageType);
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
}
