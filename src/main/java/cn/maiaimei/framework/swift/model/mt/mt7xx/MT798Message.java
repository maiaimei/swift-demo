package cn.maiaimei.framework.swift.model.mt.mt7xx;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798Message extends BaseMessage {
    @Tag("20")
    private String transactionReferenceNumber;

    @Tag("12")
    private String subMessageType;

    @Tag("77E")
    private String field77E;

    @Tag("27A")
    private String messageIndexTotal;
}
