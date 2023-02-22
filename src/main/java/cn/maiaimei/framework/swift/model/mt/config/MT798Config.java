package cn.maiaimei.framework.swift.model.mt.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798Config extends MessageConfig {
    private String indexMessageType;
    private String subMessageType;
}
