package cn.maiaimei.framework.swift.converter;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798BaseMessage;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert BaseMessage to AbstractMT
 */
@Component
public class MsToMtConverter implements Converter<BaseMessage, AbstractMT> {

    @Override
    public AbstractMT convert(BaseMessage ms) {
        AbstractMT mt = getAbstractMT(null);
        doConvert(ms, mt);
        return mt;
    }

    public AbstractMT convert(BaseMessage ms, String messageType) {
        AbstractMT mt = getAbstractMT(messageType);
        doConvert(ms, mt);
        return mt;
    }

    @SneakyThrows
    public <T extends AbstractMT> void doConvert(BaseMessage ms, T mt) {
        final List<Field> allFields = getAllDeclaredFields(ms);
        for (Field declaredField : allFields) {
            declaredField.setAccessible(Boolean.TRUE);
            Object value = declaredField.get(ms);
            handleSequenceField(mt, declaredField, value);
            handleSimpleField(mt, declaredField, value);
            declaredField.setAccessible(Boolean.FALSE);
        }
    }

    @SneakyThrows
    private <T extends AbstractMT> void handleSequenceField(T mt, Field declaredField, Object value) {
        Sequence sequenceAnnotation = declaredField.getAnnotation(Sequence.class);
        if (sequenceAnnotation == null || value == null) {
            return;
        }
        List<Field> sequenceFields = ReflectionUtils.getSelfDeclaredFields(value.getClass());
        for (Field sequenceField : sequenceFields) {
            sequenceField.setAccessible(Boolean.TRUE);
            handleSimpleField(mt, sequenceField, sequenceField.get(value));
            sequenceField.setAccessible(Boolean.FALSE);
        }
    }

    @SneakyThrows
    private <T extends AbstractMT> void handleSimpleField(T mt, Field declaredField, Object value) {
        Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
        if (tagAnnotation != null && value != null) {
            mt.append(new com.prowidesoftware.swift.model.Tag(tagAnnotation.value(), value.toString()));
        }
    }

    private List<Field> getAllDeclaredFields(BaseMessage ms) {
        List<Field> selfFields = ReflectionUtils.getSelfDeclaredFields(ms.getClass());
        List<Field> superclassDeclaredFields = ReflectionUtils.getSelfDeclaredFields(MT798BaseMessage.class);
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(superclassDeclaredFields);
        allFields.addAll(selfFields);
        return allFields;
    }

    private AbstractMT getAbstractMT(String messageType) {
        return new AbstractMT() {
            @Override
            public String getMessageType() {
                return messageType;
            }
        };
    }
}
