package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;

public interface FieldValidatorHandler {
    void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value);

    FieldValidatorHandler getNextValidationHandler();

    default void handleNextValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        FieldValidatorHandler nextFieldValidatorHandler = getNextValidationHandler();
        if (nextFieldValidatorHandler != null) {
            nextFieldValidatorHandler.handleValidation(result, fieldComponentInfo, field, label, value);
        }
    }
}
