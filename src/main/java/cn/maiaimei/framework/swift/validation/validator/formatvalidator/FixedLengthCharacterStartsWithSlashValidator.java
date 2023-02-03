package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import org.springframework.stereotype.Component;

/**
 * Validate fixed length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, and starts with slash
 */
@Component
public class FixedLengthCharacterStartsWithSlashValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchFixedLengthCharacterStartsWithSlash(format);
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        String format = validationInfo.getFormat();
        int length = getLength(format);
        String type = getType(format);
        String valueToValidate = trimStartSlash(format, value);
        if (!ValidatorUtils.validateFixedLengthCharacter(length, type, valueToValidate)) {
            return ValidationError.mustBeFixedLengthCharacterStartsWithSlash(label, length, type, value);
        }
        return null;
    }
}
