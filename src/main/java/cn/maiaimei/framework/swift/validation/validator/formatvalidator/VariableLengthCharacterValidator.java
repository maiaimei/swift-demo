package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class VariableLengthCharacterValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return ValidatorUtils.isMatchVariableLengthCharacter(format);
    }

    @Override
    public String validate(FieldComponentInfo validationInfo, Field field, String label, String value) {
        String format = validationInfo.getFormat();
        int length = getLength(format);
        String type = getType(format);
        if (!ValidatorUtils.validateVariableLengthCharacter(length, type, value)) {
            return ValidationError.mustBeVariableLengthCharacter(label, length, type, value);
        }
        return null;
    }
}
