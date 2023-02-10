package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.SwiftTag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798Message extends BaseMessage {
    @SwiftTag("20")
    private String transactionReferenceNumber;

    @SwiftTag("12")
    private String subMessageType;

    @SwiftTag("27A")
    private String messageIndexTotal;

    @SwiftTag(value = "27A", index = 1)
    private String messageIndex;

    @SwiftTag(value = "27A", index = 2)
    private String messageTotal;
}
