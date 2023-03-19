package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

/** Validate field by format */
public interface FormatFieldValidator extends FieldValidator {
    boolean supportsFormat(Field field, String format);
}
