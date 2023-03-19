package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

/** Validate field by pattern */
public interface PatternFieldValidator extends FieldValidator {
    boolean supportsPattern(Field field, String pattern);
}
