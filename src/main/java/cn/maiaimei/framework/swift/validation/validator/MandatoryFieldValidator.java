package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validate mandatory field
 */
@Component
public class MandatoryFieldValidator implements FieldValidator {

    @Override
    public boolean supportsFormat(Field field, String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(Field field, String pattern) {
        return false;
    }

    @Override
    public boolean supportsType(Field field, String type) {
        return false;
    }

    @Override
    public String validate(FieldComponentInfo validationInfo, Field field, String label, String value) {
        if (ValidatorUtils.isMandatory(validationInfo.getStatus()) && StringUtils.isBlank(value)) {
            return ValidationError.mustNotBeBlank(label);
        }
        return null;
    }
}
