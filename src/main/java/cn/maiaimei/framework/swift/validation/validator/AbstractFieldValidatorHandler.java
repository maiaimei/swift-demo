package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;

public abstract class AbstractFieldValidatorHandler {
    private AbstractFieldValidatorHandler nextValidationHandler;

    protected void setNextValidationHandler(AbstractFieldValidatorHandler nextValidationHandler) {
        this.nextValidationHandler = nextValidationHandler;
    }

    protected void handleNextValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        AbstractFieldValidatorHandler nextFieldValidatorHandler = this.nextValidationHandler;
        if (nextFieldValidatorHandler != null) {
            nextFieldValidatorHandler.handleValidation(result, fieldComponentInfo, field, label, value);
        }
    }

    protected abstract void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value);
}
