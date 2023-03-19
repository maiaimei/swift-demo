package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798ExtensionMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import cn.maiaimei.framework.swift.model.mt.mt7xx.transaction.MT777Transaction;
import org.springframework.stereotype.Component;

@Component
public class MT777ToTransactionConverterStrategy
        extends AbstractMT798ToTransactionConverterStrategy {
    @Override
    protected MT798IndexMessage getIndexMessage() {
        return new MT777Transaction.MT777IndexMessage();
    }

    @Override
    protected MT798DetailMessage getDetailMessage() {
        return new MT777Transaction.MT777DetailMessage();
    }

    @Override
    protected MT798ExtensionMessage getExtensionMessage() {
        return null;
    }

    @Override
    public boolean supportsMessageType(String subMessageType) {
        return "777".equals(subMessageType);
    }

    @Override
    public <T extends MT798Transaction> Class<T> getTransactionType() {
        return (Class<T>) MT777Transaction.class;
    }
}
