package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798ExtensionMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import cn.maiaimei.framework.swift.model.mt.mt7xx.transaction.MT762Transaction;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class MT762ToTransactionConverterStrategy extends AbstractMT798ToTransactionConverterStrategy {
    @Override
    protected MT798IndexMessage getIndexMessage() {
        return new MT762Transaction.MT762IndexMessage();
    }

    @Override
    protected MT798DetailMessage getDetailMessage() {
        return new MT762Transaction.MT762DetailMessage();
    }

    @Override
    protected MT798ExtensionMessage getExtensionMessage() {
        return new MT762Transaction.MT762ExtensionMessage();
    }

    @Override
    public boolean supportsMessageType(String subMessageType) {
        return subMessageType.equals("762");
    }

    @Override
    public <T extends MT798Transaction> Class<T> getTransactionType() {
        return (Class<T>) MT762Transaction.class;
    }
}
