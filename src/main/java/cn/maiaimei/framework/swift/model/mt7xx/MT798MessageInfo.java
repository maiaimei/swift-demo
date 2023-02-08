package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.model.MessageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798MessageInfo extends MessageInfo {
    private String indexMessageType;
}
