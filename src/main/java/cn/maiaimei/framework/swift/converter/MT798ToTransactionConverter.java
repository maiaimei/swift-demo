package cn.maiaimei.framework.swift.converter;

import cn.maiaimei.framework.swift.model.mt7xx.MT798Dto;
import cn.maiaimei.framework.swift.model.mt7xx.MT798Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MT798ToTransactionConverter implements Converter<MT798Dto, MT798Transaction> {
    @Override
    public MT798Transaction convert(MT798Dto source) {
        return null;
    }
}
