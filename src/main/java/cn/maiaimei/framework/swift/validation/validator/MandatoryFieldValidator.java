package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validate mandatory field
 */
@Component
public class MandatoryFieldValidator implements FieldValidator {

    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(String pattern) {
        return false;
    }

    @Override
    public boolean supportsType(String type) {
        return false;
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        if (validationInfo.isMandatory() && StringUtils.isBlank(value)) {
            return ValidationError.mustNotBeBlank(label);
        }
        return null;
    }
}
