package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798ExtensionMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import cn.maiaimei.framework.swift.model.mt.mt7xx.transaction.MT778Transaction;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class MT778ToTransactionConverterStrategy
        extends AbstractMT798ToTransactionConverterStrategy {
    @Override
    protected MT798IndexMessage getIndexMessage() {
        return new MT778Transaction.MT778IndexMessage();
    }

    @Override
    protected MT798DetailMessage getDetailMessage() {
        return null;
    }

    @Override
    protected MT798ExtensionMessage getExtensionMessage() {
        return null;
    }

    @Override
    public boolean supportsMessageType(String subMessageType) {
        return "778".equals(subMessageType);
    }

    @Override
    public <T extends MT798Transaction> Class<T> getTransactionType() {
        return (Class<T>) MT778Transaction.class;
    }
}
