package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.validator.MandatoryFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MandatoryValidationHandler extends AbstractValidationHandler {

    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;

    @Autowired
    private FormatValidationHandler formatFieldValidatorHandler;

    @Override
    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String errorMessage = mandatoryFieldValidator.validate(fieldComponentInfo, field, label, value);
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
    public ValidationHandler getNextValidationHandler() {
        return formatFieldValidatorHandler;
    }
}
