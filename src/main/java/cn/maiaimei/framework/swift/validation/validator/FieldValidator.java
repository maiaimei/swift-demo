package cn.maiaimei.framework.swift.validation.validator;

import com.prowidesoftware.swift.model.field.Field;

public interface FieldValidator<T extends Field> {
    boolean supports(String nameOrFormat);

    String validate(T field, String format, String value);
}
