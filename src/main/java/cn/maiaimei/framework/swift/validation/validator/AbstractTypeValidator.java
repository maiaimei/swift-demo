package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

public abstract class AbstractTypeValidator implements FieldValidator {
    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(String pattern, Field field) {
        return false;
    }
}
