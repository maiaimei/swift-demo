package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

public interface TypeFieldValidator extends FieldValidator {
    boolean supportsType(Field field, String type);
}
