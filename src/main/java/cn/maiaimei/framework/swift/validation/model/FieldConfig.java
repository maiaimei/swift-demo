package cn.maiaimei.framework.swift.validation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldConfig {
    private String tag;
    private String format;
    private boolean required;
    private List<ValidationRule> rules;
    private List<FieldComponentConfig> components;
}
