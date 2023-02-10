package cn.maiaimei.framework.swift.validation.config;

import lombok.Data;

@Data
public class RuleInfo {
    /**
     * Spel expressionString
     */
    private String expressionString;
    private String beanName;
    private String errorMessage;
}
