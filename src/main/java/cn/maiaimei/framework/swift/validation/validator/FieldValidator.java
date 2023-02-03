package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import com.prowidesoftware.swift.model.field.Field;

public interface FieldValidator {
    boolean supportsFormat(String format);

    boolean supportsPattern(String pattern);

    boolean supportsType(String type);

    String validate(BaseValidationInfo validationInfo, String label, String value);

    default void validate(ValidationResult result, Field field, FieldInfo fieldInfo, String label, String value) {
    }
}
