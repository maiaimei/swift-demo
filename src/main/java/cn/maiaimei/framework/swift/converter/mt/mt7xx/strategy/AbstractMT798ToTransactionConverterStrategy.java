package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.annotation.WithSequence;
import cn.maiaimei.framework.swift.exception.ProcessorNotFoundException;
import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.processor.mt.mt7xx.AbstractMT798SequenceProcessor;
import cn.maiaimei.framework.swift.util.ReflectionUtils;
import cn.maiaimei.framework.swift.util.SwiftUtils;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractMT798ToTransactionConverterStrategy implements MT798ToTransactionConverterStrategy {

    @Autowired
    private SwiftUtils swiftUtils;

    @Autowired
    private Set<AbstractMT798SequenceProcessor> mt798SequenceProcessorSet;

    @SneakyThrows
    @Override
    public <T extends MT798Transaction> T convert(MT798 indexMessage, List<MT798> detailMessages, List<MT798> extensionMessages, Class<T> transactionType) {
        Method[] declaredMethods = transactionType.getDeclaredMethods();
        Method setIndexMessage = ReflectionUtils.obtainSetIndexMessageMethod(declaredMethods);
        Method setDetailMessages = ReflectionUtils.obtainSetDetailMessagesMethod(declaredMethods);
        Method setExtensionMessages = ReflectionUtils.obtainSetExtensionMessagesMethod(declaredMethods);

        Constructor<T> constructor = transactionType.getConstructor();
        T instance = constructor.newInstance();
        if (setIndexMessage != null) {
            MT798IndexMessage indexMsg = mt798ToMessage(indexMessage, this::getIndexMessage);
            setIndexMessage.invoke(instance, indexMsg);
        }
        if (setDetailMessages != null) {
            List<MT798DetailMessage> detailMsgs = mt798ToMessage(detailMessages, this::getDetailMessage);
            setDetailMessages.invoke(instance, detailMsgs);
        }
        if (setExtensionMessages != null) {
            List<MT798ExtensionMessage> extensionMsgs = mt798ToMessage(extensionMessages, this::getExtensionMessage);
            setExtensionMessages.invoke(instance, extensionMsgs);
        }
        // TODO: LinkedMessages
        return instance;
    }

    protected abstract MT798IndexMessage getIndexMessage();

    protected abstract MT798DetailMessage getDetailMessage();

    protected abstract MT798ExtensionMessage getExtensionMessage();

    private <T extends MT798BaseMessage> List<T> mt798ToMessage(List<MT798> mts, Supplier<T> spplier) {
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

    @SneakyThrows
    private <T extends MT798BaseMessage> T mt798ToMessage(MT798 mt, Supplier<T> spplier) {
        T message = spplier.get();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        SwiftTagListBlock block = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        String transactionReferenceNumber = mt.getField20().getValue();
        String subMessageType = mt.getField12().getValue();
        message.setTransactionReferenceNumber(transactionReferenceNumber);
        message.setSubMessageType(subMessageType);
        if (message.getClass().isAnnotationPresent(WithSequence.class)) {
            AbstractMT798SequenceProcessor sequenceProcessor = getSequenceProcessor(subMessageType);
            Map<String, List<SwiftTagListBlock>> sequenceMap = sequenceProcessor.getSequenceMap(mt);
            swiftUtils.populateMessage(message, block, sequenceMap);
        } else {
            swiftUtils.populateMessage(message, block);
        }
        return message;
    }

    private AbstractMT798SequenceProcessor getSequenceProcessor(String subMessageType) {
        for (AbstractMT798SequenceProcessor processor : mt798SequenceProcessorSet) {
            if (processor.supportsMessageType(subMessageType)) {
                return processor;
            }
        }
        throw new ProcessorNotFoundException("Can't found MessageSequenceProcessor for MT" + subMessageType);
    }
}
