package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import com.prowidesoftware.swift.model.field.Field;

public interface FormatValidatorStrategy<T extends Field> {
    boolean supportsFormat(String format);

    String validate(T field, String tag, String format, String value);
}
