package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;

import java.util.List;

@Data
public class MessageValidationCfg {
    private String messageType;
    private List<FieldInfo> fields;
    private List<SequenceInfo> sequences;
    private List<String> rules;
}
