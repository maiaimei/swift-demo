package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;

public interface ValidationHandler {
    void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value);

    ValidationHandler getNextValidationHandler();
}
