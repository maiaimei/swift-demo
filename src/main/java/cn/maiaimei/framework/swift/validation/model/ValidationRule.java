package cn.maiaimei.framework.swift.validation.model;

import lombok.Data;

import java.util.List;

@Data
public class ValidationRule {
    @Deprecated
    private Integer key;
    private String type;
    private String datetimePattern;
    private List<String> enumItems;
}
