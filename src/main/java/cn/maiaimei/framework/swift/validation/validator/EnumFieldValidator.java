package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
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
        List<String> options = validationInfo.getOptions();
        if (!CollectionUtils.isEmpty(options) && !options.contains(value)) {
            return ValidationError.mustIn(label, value, options);
        }
        return null;
    }
}
