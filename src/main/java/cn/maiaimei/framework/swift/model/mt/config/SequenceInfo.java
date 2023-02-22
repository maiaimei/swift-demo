package cn.maiaimei.framework.swift.model.mt.config;

import lombok.Data;

import java.util.List;

@Data
public class SequenceInfo {
    private String name;
    private String status;
    private List<FieldInfo> fields;
    private List<RuleInfo> rules;
}
