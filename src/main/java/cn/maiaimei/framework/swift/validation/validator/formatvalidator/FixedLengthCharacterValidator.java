package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import org.springframework.stereotype.Component;

/**
 * Validate fixed length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals
 */
@Component
public class FixedLengthCharacterValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchFixedLengthCharacter(format);
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        String format = validationInfo.getFormat();
        int length = getLength(format);
        String type = getType(format);
        if (!ValidatorUtils.validateFixedLengthCharacter(length, type, value)) {
            return ValidationError.mustBeFixedLengthCharacter(label, length, type, value);
        }
        return null;
    }
}
