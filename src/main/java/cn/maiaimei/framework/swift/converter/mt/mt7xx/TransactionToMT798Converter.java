package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import cn.maiaimei.framework.swift.util.SwiftUtils;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// TODO: refactor TransactionToMT798Converter
@SuppressWarnings("unchecked")
@Component
public class TransactionToMT798Converter {

    @Autowired
    private SwiftUtils swiftUtils;

    @SneakyThrows
    public <T extends MT798Transaction> MT798Message convert(T transaction, Class<T> transactionType) {
        Method[] declaredMethods = transactionType.getDeclaredMethods();
        Method getIndexMessage = ReflectionUtils.obtainGetIndexMessageMethod(declaredMethods);
        Method getDetailMessages = ReflectionUtils.obtainGetDetailMessagesMethod(declaredMethods);
        Method getExtensionMessages = ReflectionUtils.obtainGetExtensionMessagesMethod(declaredMethods);

        MT798Message mt798Message = new MT798Message();
        if (getIndexMessage != null) {
            MT798IndexMessage indexMessage = (MT798IndexMessage) getIndexMessage.invoke(transaction);
            MT798 mt798 = new MT798();
            swiftUtils.convert(indexMessage, mt798);
            mt798Message.setIndexMessage(mt798);
        }

        if (getDetailMessages != null) {
            List<MT798DetailMessage> detailMessages = (List<MT798DetailMessage>) getDetailMessages.invoke(transaction);
            if (!CollectionUtils.isEmpty(detailMessages)) {
                mt798Message.setDetailMessages(new ArrayList<>());
                for (MT798DetailMessage detailMessage : detailMessages) {
                    if (detailMessage == null) {
                        continue;
                    }
                    MT798 mt798 = new MT798();
                    swiftUtils.convert(detailMessage, mt798);
                    mt798Message.getDetailMessages().add(mt798);
                }
            }
        }

        if (getExtensionMessages != null) {
            List<MT798ExtensionMessage> extensionMessages = (List<MT798ExtensionMessage>) getExtensionMessages.invoke(transaction);
            if (!CollectionUtils.isEmpty(extensionMessages)) {
                mt798Message.setExtensionMessages(new ArrayList<>());
                for (MT798ExtensionMessage extensionMessage : extensionMessages) {
                    if (extensionMessage == null) {
                        continue;
                    }
                    MT798 mt798 = new MT798();
                    swiftUtils.convert(extensionMessage, mt798);
                    mt798Message.getExtensionMessages().add(mt798);
                }
            }
        }

        return mt798Message;
    }

}
