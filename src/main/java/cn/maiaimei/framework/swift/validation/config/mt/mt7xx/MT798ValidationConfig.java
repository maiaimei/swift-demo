package cn.maiaimei.framework.swift.validation.config.mt.mt7xx;

import cn.maiaimei.framework.swift.validation.config.MessageValidationConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798ValidationConfig extends MessageValidationConfig {
    private String indexMessageType;
}
