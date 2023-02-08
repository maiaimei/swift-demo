package cn.maiaimei.framework.swift.model;

import lombok.Data;

import java.util.List;

@Data
public class SequenceInfo {
    private String name;
    private String status;
    private List<FieldInfo> fields;
}
