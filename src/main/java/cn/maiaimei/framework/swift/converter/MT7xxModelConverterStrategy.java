package cn.maiaimei.framework.swift.converter;

import cn.maiaimei.framework.swift.model.MT7xxModel;
import cn.maiaimei.framework.swift.model.MessageRecord;

import java.util.List;

public abstract class MT7xxModelConverterStrategy {
    public MT7xxModel doConvert(List<MessageRecord> messageRecords) {
        MT7xxModel mt7xxModel = createMT7xxModel();
        return mt7xxModel;
    }

    public void afterConvert(MT7xxModel mt7xxModel) {
    }

    public abstract MT7xxModel createMT7xxModel();
}
