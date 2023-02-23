package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.validator.FormatFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FormatValidationHandler extends AbstractValidationHandler {
    @Autowired
    private Set<FormatFieldValidator> formatValidatorSet;

    @Autowired
    private PatternValidationHandler patternFieldValidatorHandler;

    @Override
    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        for (FormatFieldValidator formatValidator : formatValidatorSet) {
            if (formatValidator.supportsFormat(field, fieldComponentInfo.getFormat())) {
                String errorMessage = formatValidator.validate(fieldComponentInfo, field, label, value);
                if (StringUtils.isNotBlank(errorMessage)) {
                    result.addErrorMessage(errorMessage);
                    return;
                }
                break;
            }
        }
        handleNextValidation(result, fieldComponentInfo, field, label, value);
    }

    @Override
    public ValidationHandler getNextValidationHandler() {
        return patternFieldValidatorHandler;
    }
}
