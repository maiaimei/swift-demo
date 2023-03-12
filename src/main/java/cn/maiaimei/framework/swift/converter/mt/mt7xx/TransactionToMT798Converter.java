package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionToMT798Converter implements Converter<MT798Transaction, MT798Message> {

    @Override
    public MT798Message convert(MT798Transaction transaction) {
        MT798IndexMessage indexMessage = transaction.getIndexMessage();
        List<? extends MT798DetailMessage> detailMessages = transaction.getDetailMessages();
        List<? extends MT798ExtensionMessage> extensionMessages = transaction.getExtensionMessages();

        MT798Message mt798Message = new MT798Message();
        mt798Message.setIndexMessage(convert(indexMessage));
        if (!CollectionUtils.isEmpty(detailMessages)) {
            mt798Message.setDetailMessages(new ArrayList<>());
            for (MT798DetailMessage detailMessage : detailMessages) {
                mt798Message.getDetailMessages().add(convert(detailMessage));
            }
        }
        if (!CollectionUtils.isEmpty(extensionMessages)) {
            mt798Message.setExtensionMessages(new ArrayList<>());
            for (MT798ExtensionMessage extensionMessage : extensionMessages) {
                mt798Message.getExtensionMessages().add(convert(extensionMessage));
            }
        }

        return mt798Message;
    }

    @SneakyThrows
    private MT798 convert(BaseMessage baseMessage) {
        MT798 mt798 = new MT798();
        List<Field> selfFields = ReflectionUtils.getSelfDeclaredFields(baseMessage.getClass());
        List<Field> superclassDeclaredFields = ReflectionUtils.getSuperclassDeclaredFields(baseMessage.getClass(), "MT798BaseMessage");
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(superclassDeclaredFields);
        allFields.addAll(selfFields);
        for (Field declaredField : allFields) {
            declaredField.setAccessible(Boolean.TRUE);
            Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
            Object tagValue = declaredField.get(baseMessage);
            if (tagValue != null) {
                mt798.append(new com.prowidesoftware.swift.model.Tag(tagAnnotation.value(), tagValue.toString()));
            }
            declaredField.setAccessible(Boolean.FALSE);
        }
        return mt798;
    }

}
