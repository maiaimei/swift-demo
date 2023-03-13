package cn.maiaimei.framework.swift.converter.mt.mt7xx.strategy;

import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

import java.util.List;

public interface MT798ToTransactionConverterStrategy {
    boolean supportsMessageType(String subMessageType);

    MT798Transaction convert(MT798 indexMessage, List<MT798> detailMessages, List<MT798> extensionMessages);
}
