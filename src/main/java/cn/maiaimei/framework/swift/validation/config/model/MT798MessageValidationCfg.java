package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798MessageValidationCfg extends MessageValidationCfg {
    private String indexMessageType;
}
