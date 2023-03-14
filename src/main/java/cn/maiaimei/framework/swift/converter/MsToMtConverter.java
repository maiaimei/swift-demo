package cn.maiaimei.framework.swift.converter;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798BaseMessage;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class MsToMtConverter {

    @SneakyThrows
    public void convert(BaseMessage ms, AbstractMT mt) {
        List<Field> selfFields = ReflectionUtils.getSelfDeclaredFields(ms.getClass());
        List<Field> superclassDeclaredFields = ReflectionUtils.getSuperclassDeclaredFields(ms.getClass(), MT798BaseMessage.class);
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(superclassDeclaredFields);
        allFields.addAll(selfFields);
        for (Field declaredField : allFields) {
            declaredField.setAccessible(Boolean.TRUE);
            Object value = declaredField.get(ms);
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
}
