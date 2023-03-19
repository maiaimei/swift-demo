package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.converter.MtToMsConverter;
import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field27A;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractMT798ToTransactionConverterStrategy
        implements MT798ToTransactionConverterStrategy {

    @Autowired private MtToMsConverter mtToMsConverter;

    @SneakyThrows
    @Override
    public <T extends MT798Transaction> T convert(
            MT798 indexMessage,
            List<MT798> detailMessages,
            List<MT798> extensionMessages,
            Class<T> transactionType) {
        Method setIndexMessageMethod =
                ReflectionUtils.findDeclaredMethod(transactionType, "setIndexMessage");
        Method setDetailMessagesMethod =
                ReflectionUtils.findDeclaredMethod(transactionType, "setDetailMessages");
        Method setExtensionMessagesMethod =
                ReflectionUtils.findDeclaredMethod(transactionType, "setExtensionMessages");

        Constructor<T> constructor = transactionType.getConstructor();
        T instance = constructor.newInstance();
        invoke(
                instance,
                setIndexMessageMethod,
                Collections.singletonList(indexMessage),
                this::getIndexMessage);
        invoke(instance, setDetailMessagesMethod, detailMessages, this::getDetailMessage);
        invoke(instance, setExtensionMessagesMethod, extensionMessages, this::getExtensionMessage);
        return instance;
    }

    protected abstract MT798IndexMessage getIndexMessage();

    protected abstract MT798DetailMessage getDetailMessage();

    protected abstract MT798ExtensionMessage getExtensionMessage();

    private <T extends MT798Transaction, M extends MT798BaseMessage> void invoke(
            T instance, Method method, List<MT798> mts, Supplier<M> spplier) {
        if (method == null) {
            return;
        }
        List<M> ms = mt798ToMessage(mts, spplier);
        if (CollectionUtils.isEmpty(ms)) {
            return;
        }
        try {
            if ("setIndexMessage".equals(method.getName())) {
                method.invoke(instance, ms.get(0));
            } else {
                method.invoke(instance, ms);
            }
        } catch (Exception ex) {
            log.error("invoke method {} error", method.getName(), ex);
        }
    }

    private <T extends MT798BaseMessage> List<T> mt798ToMessage(
            List<MT798> mts, Supplier<T> spplier) {
        List<T> msgs = null;
        if (!CollectionUtils.isEmpty(mts)) {
            msgs = new ArrayList<>();
            for (MT798 mt798 : mts) {
                T msg = mt798ToMessage(mt798, spplier);
                msgs.add(msg);
            }
        }
        return msgs;
    }

    private <T extends MT798BaseMessage> T mt798ToMessage(MT798 mt, Supplier<T> spplier) {
        T message = spplier.get();
        if (message == null) {
            return message;
        }
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        SwiftTagListBlock block = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        String transactionReferenceNumber = mt.getField20().getValue();
        String subMessageType = mt.getField12().getValue();
        message.setSection1TransactionReferenceNumber(transactionReferenceNumber);
        message.setSubMessageType(subMessageType);
        pupulateMessageIndexTotal(message, block);
        mtToMsConverter.convert(message, mt, block, subMessageType);
        return message;
    }

    @SneakyThrows
    private <T extends MT798BaseMessage> void pupulateMessageIndexTotal(
            T message, SwiftTagListBlock block) {
        final Field messageIndexTotalField =
                ReflectionUtils.getDeclaredField(MT798BaseMessage.class, "messageIndexTotal");
        if (messageIndexTotalField != null) {
            messageIndexTotalField.setAccessible(Boolean.TRUE);
            messageIndexTotalField.set(message, block.getTagValue(Field27A.NAME));
            messageIndexTotalField.setAccessible(Boolean.FALSE);
        }
    }
}
