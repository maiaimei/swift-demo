package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.SwiftMTTag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseMT798Message extends BaseMessage {
    @SwiftMTTag("20")
    private String transactionReferenceNumber;

    @SwiftMTTag("12")
    private String subMessageType;

    @SwiftMTTag("27A")
    private String messageIndexTotal;
}
