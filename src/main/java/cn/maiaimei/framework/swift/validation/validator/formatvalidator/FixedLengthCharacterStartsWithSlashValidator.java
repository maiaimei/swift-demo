package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.model.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

/**
 * Validate fixed length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, and starts with slash
 */
@Component
public class FixedLengthCharacterStartsWithSlashValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return ValidatorUtils.isMatchFixedLengthCharacterStartsWithSlash(format);
    }

    @Override
    public String validate(FieldComponentInfo validationInfo, Field field, String label, String value) {
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
