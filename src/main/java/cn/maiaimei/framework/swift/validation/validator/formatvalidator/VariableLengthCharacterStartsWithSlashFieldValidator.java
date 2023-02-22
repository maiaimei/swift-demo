package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.FormatFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class VariableLengthCharacterStartsWithSlashFieldValidator implements FormatFieldValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return ValidatorUtils.isVariableLengthCharacterStartsWithSlash(format);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String format = fieldComponentInfo.getFormat();
        int length = ValidatorUtils.getNumber(format);
        String type = ValidatorUtils.getType(format);
        String valueToValidate = ValidatorUtils.trimStartSlash(format, value);
        if (ValidatorUtils.gt(value, length) || ValidatorUtils.containsOther(value, type)) {
            return ValidationError.mustBeVariableLengthCharacterStartsWithSlash(label, length, type, value);
        }
        return null;
    }
}
