package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.validator.FormatFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

/**
 * Validate fixed length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals
 */
@Component
public class FixedLengthCharacterFieldValidator implements FormatFieldValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return ValidatorUtils.isMatchFixedLengthCharacter(format);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String format = fieldComponentInfo.getFormat();
        int length = ValidatorUtils.getNumber(format);
        String type = ValidatorUtils.getType(format);
        if (!ValidatorUtils.validateFixedLengthCharacter(length, type, value)) {
            return ValidationError.mustBeFixedLengthCharacter(label, length, type, value);
        }
        return null;
    }
}
