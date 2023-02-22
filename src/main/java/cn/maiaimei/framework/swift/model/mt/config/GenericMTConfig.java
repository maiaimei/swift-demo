package cn.maiaimei.framework.swift.model.mt.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenericMTConfig extends MessageConfig {
    private String messageType;
}
