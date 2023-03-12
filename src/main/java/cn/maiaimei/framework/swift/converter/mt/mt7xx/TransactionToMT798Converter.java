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

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionToMT798Converter implements Converter<MT798Transaction, MT798Messages> {

    @Override
    public MT798Messages convert(MT798Transaction transaction) {
        MT798IndexMessage indexMessage = transaction.getIndexMessage();
        List<? extends MT798DetailMessage> detailMessages = transaction.getDetailMessages();
        List<? extends MT798ExtensionMessage> extensionMessages = transaction.getExtensionMessages();

        MT798Messages mt798Messages = new MT798Messages();
        mt798Messages.setIndexMessage(convert(indexMessage));
        if (!CollectionUtils.isEmpty(detailMessages)) {
            mt798Messages.setDetailMessages(new ArrayList<>());
            for (MT798DetailMessage detailMessage : detailMessages) {
                mt798Messages.getDetailMessages().add(convert(detailMessage));
            }
        }
        if (!CollectionUtils.isEmpty(extensionMessages)) {
            mt798Messages.setExtensionMessages(new ArrayList<>());
            for (MT798ExtensionMessage extensionMessage : extensionMessages) {
                mt798Messages.getExtensionMessages().add(convert(extensionMessage));
            }
        }

        return mt798Messages;
    }

    @SneakyThrows
    private MT798 convert(BaseMessage baseMessage) {
        MT798 mt798 = new MT798();
        List<java.lang.reflect.Field> declaredFields = ReflectionUtils.getDeclaredFields(baseMessage.getClass());
        for (java.lang.reflect.Field declaredField : declaredFields) {
            declaredField.setAccessible(Boolean.TRUE);
            Tag tagAnnotation = declaredField.getAnnotation(Tag.class);
            if (tagAnnotation.tags().length == 0) {
                Object tagValue = declaredField.get(baseMessage);
                if (tagValue != null) {
                    mt798.append(new com.prowidesoftware.swift.model.Tag(tagAnnotation.value(), tagValue.toString()));
                }
            }
            declaredField.setAccessible(Boolean.FALSE);
        }
        return mt798;
    }

}
