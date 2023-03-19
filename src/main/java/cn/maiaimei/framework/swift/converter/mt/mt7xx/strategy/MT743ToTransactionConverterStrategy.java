package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798ExtensionMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import cn.maiaimei.framework.swift.model.mt.mt7xx.transaction.MT743Transaction;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class MT743ToTransactionConverterStrategy
        extends AbstractMT798ToTransactionConverterStrategy {
    @Override
    protected MT798IndexMessage getIndexMessage() {
        return new MT743Transaction.MT743IndexMessage();
    }

    @Override
    protected MT798DetailMessage getDetailMessage() {
        return new MT743Transaction.MT743DetailMessage();
    }

    @Override
    protected MT798ExtensionMessage getExtensionMessage() {
        return new MT743Transaction.MT743ExtensionMessage();
    }

    @Override
    public boolean supportsMessageType(String subMessageType) {
        return "743".equals(subMessageType);
    }

    @Override
    public <T extends MT798Transaction> Class<T> getTransactionType() {
        return (Class<T>) MT743Transaction.class;
    }
}
