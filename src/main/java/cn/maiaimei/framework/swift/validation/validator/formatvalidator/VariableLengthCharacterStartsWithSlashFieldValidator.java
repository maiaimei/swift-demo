package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.model.mt.config.FieldInfo;
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
    public String validate(
            FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String format = fieldComponentInfo.getFormat();
        int maxlength = ValidatorUtils.getNumber(format);
        String type = ValidatorUtils.getType(format);
        String valueToValidate = value;
        if (!ValidatorUtils.isStartsWithSlash(value)) {
            if (FieldInfo.class.isAssignableFrom(fieldComponentInfo.getClass())) {
                return ValidationError.mustBeVariableLengthCharacterStartsWithSlash(
                        label, maxlength, type, value);
            }
        } else {
            valueToValidate = ValidatorUtils.trimStartSlash(format, value);
        }
        if (ValidatorUtils.gt(valueToValidate, maxlength)
                || ValidatorUtils.containsOther(valueToValidate, type)) {
            return ValidationError.mustBeVariableLengthCharacterStartsWithSlash(
                    label, maxlength, type, value);
        }
        return null;
    }
}
