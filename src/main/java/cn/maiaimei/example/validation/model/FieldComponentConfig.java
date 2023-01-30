package cn.maiaimei.example.validation.model;

import lombok.Data;

import java.util.List;

@Data
public class FieldComponentConfig {
    private int index;
    private String format;
    private boolean required;
    private List<ValidationRule> rules;
}
