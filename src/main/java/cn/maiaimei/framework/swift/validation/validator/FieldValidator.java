package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import com.prowidesoftware.swift.model.field.Field;

public interface FieldValidator {
    boolean supportsFormat(String format);

    boolean supportsPattern(String pattern, Field field);

    boolean supportsType(String type);

    String validate(BaseValidationInfo validationInfo, String label, String value);

    default String validate(BaseValidationInfo validationInfo, Field field, String label, String value) {
        return null;
    }

    default void validate(ValidationResult result, FieldInfo fieldInfo, Field field, String label, String value) {
    }
}
