package cn.maiaimei.example.validation.validator;

import cn.maiaimei.example.validation.ValidationConfigItem;
import cn.maiaimei.example.validation.ValidatorUtils;
import org.springframework.stereotype.Component;

@Component
public class GenericFieldValidator implements FieldValidator {
    @Override
    public boolean supports(ValidationConfigItem configItem) {
        return false;
    }

    @Override
    public boolean validate(ValidationConfigItem configItem, String value) {
        if (ValidatorUtils.isMatchCompositeFormat(configItem.getFormat())) {
            return ValidatorUtils.validateCompositeFormat(configItem, value);
        }
        return false;
    }
}
