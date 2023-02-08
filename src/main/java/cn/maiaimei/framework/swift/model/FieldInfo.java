package cn.maiaimei.framework.swift.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FieldInfo extends FieldComponentInfo {
    private String no;
    private String tag;
    private String fieldName;
    private List<ComponentInfo> components;
    private List<FieldInfo> fields;
    private List<RuleInfo> rules;
}
