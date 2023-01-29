package cn.maiaimei.example.validation.validator;

import cn.maiaimei.example.validation.ValidationConfigItem;

public interface FieldValidator {
    boolean supports(ValidationConfigItem configItem);

    boolean validate(ValidationConfigItem configItem, String value);
}
