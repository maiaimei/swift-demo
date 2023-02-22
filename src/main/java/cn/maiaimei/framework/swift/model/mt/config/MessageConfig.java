package cn.maiaimei.framework.swift.model.mt.config;

import lombok.Data;

import java.util.List;

@Data
public class MessageConfig {
    private Boolean bankToCorporate;
    private Boolean corporateToBank;
    private List<FieldInfo> fields;
    private List<SequenceInfo> sequences;
    private List<RuleInfo> rules;
}
