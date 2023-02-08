package cn.maiaimei.framework.swift.model;

import lombok.Data;

@Data
public class RuleInfo {
    /**
     * Spel expressionString
     */
    private String expressionString;
    private String beanName;
    private String methodName;
    private String errorMessage;
}
