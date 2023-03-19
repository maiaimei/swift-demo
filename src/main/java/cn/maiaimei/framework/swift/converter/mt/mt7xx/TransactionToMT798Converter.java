package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** Convert Transaction to MT798 set */
@SuppressWarnings("unchecked")
@Component
public class TransactionToMT798Converter {

    @Autowired private MsToMT798Converter msToMT798Converter;

    @SneakyThrows
    public <T extends MT798Transaction> MT798Packets convert(
            T transaction, Class<T> transactionType) {
        Method getIndexMessage =
                ReflectionUtils.findDeclaredMethod(transactionType, "getIndexMessage");
        Method getDetailMessages =
                ReflectionUtils.findDeclaredMethod(transactionType, "getDetailMessages");
        Method getExtensionMessages =
                ReflectionUtils.findDeclaredMethod(transactionType, "getExtensionMessages");

        MT798Packets mt798Packets = new MT798Packets();

        if (getIndexMessage != null) {
            MT798IndexMessage message = (MT798IndexMessage) getIndexMessage.invoke(transaction);
            mt798Packets.setIndexMessage(doConvert(message));
        }

        if (getDetailMessages != null) {
            List<MT798DetailMessage> messages =
                    (List<MT798DetailMessage>) getDetailMessages.invoke(transaction);
            if (!CollectionUtils.isEmpty(messages)) {
                mt798Packets.setDetailMessages(doConvert(messages));
            }
        }

        if (getExtensionMessages != null) {
            List<MT798ExtensionMessage> messages =
                    (List<MT798ExtensionMessage>) getExtensionMessages.invoke(transaction);
            if (!CollectionUtils.isEmpty(messages)) {
                mt798Packets.setExtensionMessages(doConvert(messages));
            }
        }

        return mt798Packets;
    }

    private <T extends MT798BaseMessage> List<MT798> doConvert(List<T> messages) {
        List<MT798> mt798s = new ArrayList<>();
        for (MT798BaseMessage message : messages) {
            if (message == null) {
                continue;
            }
            mt798s.add(doConvert(message));
        }
        return mt798s;
    }

    private MT798 doConvert(MT798BaseMessage message) {
        final AbstractMT mt = msToMT798Converter.convert(message);
        if (mt == null) {
            return null;
        }
        final String msg = mt.message();
        return new MT798(msg);
    }
}
