package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import org.springframework.stereotype.Component;

@Component
public class VariableLengthCharacterStartsWithSlashValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchVariableLengthCharacterStartsWithSlash(format);
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        String format = validationInfo.getFormat();
        int length = getLength(format);
        String type = getType(format);
        String valueToValidate = trimStartSlash(format, value);
        if (!ValidatorUtils.validateVariableLengthCharacter(length, type, valueToValidate)) {
            return ValidationError.mustBeVariableLengthCharacterStartsWithSlash(label, length, type, value);
        }
        return null;
    }
}
