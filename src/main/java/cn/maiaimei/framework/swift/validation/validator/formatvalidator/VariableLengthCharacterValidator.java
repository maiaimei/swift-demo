package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import org.springframework.stereotype.Component;

@Component
public class VariableLengthCharacterValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchVariableLengthCharacter(format);
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        String format = validationInfo.getFormat();
        int length = getLength(format);
        String type = getType(format);
        if (!ValidatorUtils.validateVariableLengthCharacter(length, type, value)) {
            return ValidationError.mustBeVariableLengthCharacter(label, length, type, value);
        }
        return null;
    }
}
