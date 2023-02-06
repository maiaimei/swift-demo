package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseMT798Message extends BaseMessage {
    @Tag("20")
    private String transactionReferenceNumber;

    @Tag("12")
    private String subMessageType;
    
    @Tag("27A")
    private String messageIndexTotal;
}
