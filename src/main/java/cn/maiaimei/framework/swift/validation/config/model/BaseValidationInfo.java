package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;

import java.util.List;

@Data
public class BaseValidationInfo {
    String label;
    private String format;
    private String pattern;
    private String type;
    private boolean isMandatory;
    private List<String> options;
}
