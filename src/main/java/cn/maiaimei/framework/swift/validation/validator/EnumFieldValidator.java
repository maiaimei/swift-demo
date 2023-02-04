package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Validate field by enum
 */
@Component
public class EnumFieldValidator implements FieldValidator {
    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(String pattern, Field field) {
        return false;
    }

    @Override
    public boolean supportsType(String type) {
        return false;
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        List<String> options = validationInfo.getOptions();
        if (!CollectionUtils.isEmpty(options) && !options.contains(value)) {
            return ValidationError.mustIn(label, value, options);
        }
        return null;
    }
}
