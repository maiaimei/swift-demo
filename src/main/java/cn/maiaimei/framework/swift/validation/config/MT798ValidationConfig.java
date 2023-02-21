package cn.maiaimei.framework.swift.validation.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798ValidationConfig extends MessageValidationConfig {
    private String indexMessageType;
    private String subMessageType;
}
