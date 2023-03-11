package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.model.mt.config.FieldInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MandatoryFieldValidator implements FieldValidator, FieldValidatorHandler {

    @Autowired
    private FormatFieldValidatorComposite formatFieldValidatorComposite;

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        if (StringUtils.isBlank(value)) {
            if (FieldInfo.class.isAssignableFrom(fieldComponentInfo.getClass())
                    && value == null
                    && ValidatorUtils.isRequired(fieldComponentInfo.getStatus())) {
                return ValidationError.mustBePresent(label);
            }
            if (ValidatorUtils.isMandatory(fieldComponentInfo.getStatus())) {
                return ValidationError.mustNotBlank(label);
            }
        }
        return null;
    }

    @Override
    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String errorMessage = validate(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        if (StringUtils.isBlank(value)) {
            return;
        }
        handleNextValidation(result, fieldComponentInfo, field, label, value);
    }

    @Override
    public FieldValidatorHandler getNextValidationHandler() {
        return formatFieldValidatorComposite;
    }
}
