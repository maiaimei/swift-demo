package cn.maiaimei.framework.swift.model;

import cn.maiaimei.framework.swift.annotation.SwiftMTTag;
import lombok.Data;

@Data
public class MT7xxMessage {
    @SwiftMTTag("20")
    private String transactionReferenceNumber;

    @SwiftMTTag("12")
    private String subMessageType;

    @SwiftMTTag("27A")
    private String messageIndexTotal;
}
