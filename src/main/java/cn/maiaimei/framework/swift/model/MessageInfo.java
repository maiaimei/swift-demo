package cn.maiaimei.framework.swift.model;

import lombok.Data;

import java.util.List;

@Data
public class MessageInfo {
    private String messageType;
    private List<FieldInfo> fields;
    private List<SequenceInfo> sequences;
    private List<RuleInfo> rules;
}
