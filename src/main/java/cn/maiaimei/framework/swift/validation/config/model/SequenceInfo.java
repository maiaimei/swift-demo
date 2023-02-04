package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;

import java.util.List;

@Data
public class SequenceInfo {
    private String sequenceName;
    private List<FieldInfo> fields;
    private boolean isMandatory;
}
