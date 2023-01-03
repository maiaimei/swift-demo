package cn.maiaimei.example.model;

import cn.maiaimei.example.annotation.SwiftMTTag;
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
