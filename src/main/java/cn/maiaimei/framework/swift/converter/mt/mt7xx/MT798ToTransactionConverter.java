package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy.MT798ToTransactionConverterStrategy;
import cn.maiaimei.framework.swift.exception.ConverterNotFoundException;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Message;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class MT798ToTransactionConverter implements Converter<MT798Message, MT798Transaction> {

    @Autowired
    private Set<MT798ToTransactionConverterStrategy> converterStrategySet;

    @Override
    public MT798Transaction convert(MT798Message mt798Message) {
        return doConvert(mt798Message);
    }

    private MT798Transaction doConvert(MT798Message mt798Message) {
        MT798 indexMessage = mt798Message.getIndexMessage();
        List<MT798> detailMessages = mt798Message.getDetailMessages();
        List<MT798> extensionMessages = mt798Message.getExtensionMessages();
        String subMessageType = getSubMessageType(indexMessage);
        MT798ToTransactionConverterStrategy strategy = getMT798ToTransactionConverterStrategy(subMessageType);
        return strategy.convert(indexMessage, detailMessages, extensionMessages, strategy.getTransactionType());
    }

    private String getSubMessageType(MT798 indexMessage) {
        Field12 field12 = indexMessage.getField12();
        if (field12 == null) {
            return StringUtils.EMPTY;
        }
        String subMessageType = field12.getValue();
        if (StringUtils.isBlank(subMessageType)) {
            return StringUtils.EMPTY;
        }
        return subMessageType;
    }

    private MT798ToTransactionConverterStrategy getMT798ToTransactionConverterStrategy(String subMessageType) {
        if (StringUtils.isNotBlank(subMessageType)) {
            for (MT798ToTransactionConverterStrategy strategy : converterStrategySet) {
                if (strategy.supportsMessageType(subMessageType)) {
                    return strategy;
                }
            }
        }
        throw new ConverterNotFoundException("Can't found MT798ToTransactionConverterStrategy for MT" + subMessageType);
    }

}
