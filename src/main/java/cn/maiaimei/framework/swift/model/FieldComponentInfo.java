package cn.maiaimei.framework.swift.model;

import lombok.Data;

import java.util.List;

@Data
public class FieldComponentInfo {
    private String format;
    private String status;
    private String pattern;
    private String type;
    private List<String> options;
}
