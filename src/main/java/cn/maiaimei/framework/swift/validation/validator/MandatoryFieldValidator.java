package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * validate mandatory field
 */
@Component
public class MandatoryFieldValidator<T extends Field> implements FieldValidator<T> {
    @Override
    public boolean supportsName(String name) {
        return false;
    }

    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        if (field == null) {
            return ValidationError.mustBePresent(tag);
        }
        if (StringUtils.isBlank(value)) {
            return ValidationError.mustNotBeBlank(tag);
        }
        return null;
    }
}
