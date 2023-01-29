package cn.maiaimei.example.validation.validator;

import cn.maiaimei.example.validation.ValidationConfigItem;
import cn.maiaimei.example.validation.ValidatorUtils;
import org.springframework.stereotype.Component;

@Component
public class MultilineSwiftSetFieldValidator implements FieldValidator {
    @Override
    public boolean supports(ValidationConfigItem configItem) {
        return ValidatorUtils.isMatchMultilineSwiftSet(configItem.getFormat());
    }

    @Override
    public boolean validate(ValidationConfigItem configItem, String value) {
        return ValidatorUtils.validateMultilineSwiftSet(configItem, value);
    }
}
