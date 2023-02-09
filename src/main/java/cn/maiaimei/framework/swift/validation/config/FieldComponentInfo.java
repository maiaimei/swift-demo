package cn.maiaimei.framework.swift.validation.config;

import lombok.Data;

import java.util.List;

@Data
public class FieldComponentInfo {
    private String type;
    private String format;
    private String pattern;
    private String status;
    private List<String> options;
}
