package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentInfo extends BaseValidationInfo {
    private int number;
}
