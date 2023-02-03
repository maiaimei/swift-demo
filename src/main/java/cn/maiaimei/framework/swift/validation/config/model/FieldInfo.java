package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FieldInfo extends BaseValidationInfo {
    String tag;
    private List<ComponentInfo> components;
}
