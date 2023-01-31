package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

/**
 * validate field by tag
 */
public abstract class AbstractTagNameValidator<T extends Field> implements FieldValidator<T> {
    @Override
    public boolean supportsFormat(String format) {
        return false;
    }
}
