package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.annotation.WithSequence;
import cn.maiaimei.framework.swift.exception.MTSequenceProcessorNotFoundException;
import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.model.mt.mt7xx.transaction.MT784Transaction;
import cn.maiaimei.framework.swift.processor.mt.mt7xx.AbstractMT798SequenceProcessor;
import cn.maiaimei.framework.swift.util.SwiftUtils;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractMT798ToTransactionConverterStrategy implements MT798ToTransactionConverterStrategy {

    @Autowired
    private Set<AbstractMT798SequenceProcessor> mt798SequenceProcessorSet;

    @Override
    public MT798Transaction convert(MT798 indexMessage, List<MT798> detailMessages, List<MT798> extensionMessages) {
        Supplier<MT798IndexMessage> indexSupplier = this::getMT798IndexMessage;
        Supplier<MT798DetailMessage> detailSupplier = this::getMT798DetailMessage;
        Supplier<MT798ExtensionMessage> extensionSupplier = this::getMT798ExtensionMessage;
        return doConvert(indexMessage, detailMessages, extensionMessages, indexSupplier, detailSupplier, extensionSupplier);
    }

    protected abstract MT798IndexMessage getMT798IndexMessage();

    protected abstract MT798DetailMessage getMT798DetailMessage();

    protected abstract MT798ExtensionMessage getMT798ExtensionMessage();

    private MT798Transaction doConvert(MT798 indexMessage,
                                       List<MT798> detailMessages,
                                       List<MT798> extensionMessages,
                                       Supplier<MT798IndexMessage> indexSupplier,
                                       Supplier<MT798DetailMessage> detailSupplier,
                                       Supplier<MT798ExtensionMessage> extensionSupplier) {
        MT798IndexMessage indexMsg = indexSupplier.get();
        mt798ToBaseMessage(indexMessage, indexMsg);
        List<MT798DetailMessage> detailMsgs = null;
        if (!CollectionUtils.isEmpty(detailMessages)) {
            detailMsgs = new ArrayList<>();
            for (MT798 detailMessage : detailMessages) {
                MT798DetailMessage detailMsg = detailSupplier.get();
                mt798ToBaseMessage(detailMessage, detailMsg);
                detailMsgs.add(detailMsg);
            }
        }
        List<MT798ExtensionMessage> extensionMsgs = null;
        if (!CollectionUtils.isEmpty(extensionMessages)) {
            extensionMsgs = new ArrayList<>();
            for (MT798 extensionMessage : extensionMessages) {
                MT798ExtensionMessage extensionMsg = extensionSupplier.get();
                mt798ToBaseMessage(extensionMessage, extensionMsg);
                extensionMsgs.add(extensionMsg);
            }
        }
        MT798Transaction transaction = new MT784Transaction();
        transaction.setIndexMessage(indexMsg);
        transaction.setDetailMessages(detailMsgs);
        transaction.setExtensionMessages(extensionMsgs);
        return transaction;
    }

    @SneakyThrows
    private void mt798ToBaseMessage(MT798 mt, MT798BaseMessage message) {
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        SwiftTagListBlock block = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        String transactionReferenceNumber = mt.getField20().getValue();
        String subMessageType = mt.getField12().getValue();
        message.setTransactionReferenceNumber(transactionReferenceNumber);
        message.setSubMessageType(subMessageType);
        if (message.getClass().isAnnotationPresent(WithSequence.class)) {
            AbstractMT798SequenceProcessor sequenceProcessor = getMT798SequenceProcessor(subMessageType);
            Map<String, List<SwiftTagListBlock>> sequenceMap = sequenceProcessor.getSequenceMap(mt);
            SwiftUtils.populateMessage(block, sequenceMap, message);
        } else {
            SwiftUtils.populateMessage(block, message);
        }
    }

    private AbstractMT798SequenceProcessor getMT798SequenceProcessor(String subMessageType) {
        for (AbstractMT798SequenceProcessor processor : mt798SequenceProcessorSet) {
            if (processor.supportsMessageType(subMessageType)) {
                return processor;
            }
        }
        throw new MTSequenceProcessorNotFoundException(subMessageType);
    }

}
