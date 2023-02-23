package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;

public abstract class AbstractValidationHandler implements ValidationHandler {

    protected void handleNextValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        ValidationHandler nextValidationHandler = getNextValidationHandler();
        if (nextValidationHandler != null) {
            nextValidationHandler.handleValidation(result, fieldComponentInfo, field, label, value);
        }
    }
    
}
