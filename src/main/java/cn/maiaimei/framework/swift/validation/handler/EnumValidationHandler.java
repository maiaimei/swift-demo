package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.validator.EnumFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnumValidationHandler extends AbstractValidationHandler {
    @Autowired
    private EnumFieldValidator enumFieldValidator;

    @Autowired
    private ComponentValidationHandler componentValidationHandler;

    @Override
    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String errorMessage = enumFieldValidator.validate(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        handleNextValidation(result, fieldComponentInfo, field, label, value);
    }

    @Override
    public ValidationHandler getNextValidationHandler() {
        return componentValidationHandler;
    }
}
