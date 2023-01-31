package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

/**
 * validate field by format
 */
public abstract class AbstractFormatValidator<T extends Field> implements FieldValidator<T> {
    @Override
    public boolean supportsName(String name) {
        return false;
    }
}
