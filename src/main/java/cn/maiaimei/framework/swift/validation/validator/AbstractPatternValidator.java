package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

/**
 * Validate field by pattern
 */
public abstract class AbstractPatternValidator implements FieldValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return false;
    }

    @Override
    public boolean supportsType(Field field, String type) {
        return false;
    }
}
