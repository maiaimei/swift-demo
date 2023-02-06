package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import com.prowidesoftware.swift.model.field.Field;

public interface FieldValidator {
    boolean supportsFormat(Field field, String format);

    boolean supportsPattern(Field field, String pattern);

    boolean supportsType(Field field, String type);

    String validate(BaseValidationInfo validationInfo, Field field, String label, String value);
}
