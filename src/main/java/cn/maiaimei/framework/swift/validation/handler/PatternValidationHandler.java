package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.validator.PatternFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PatternValidationHandler extends AbstractValidationHandler {
    @Autowired
    private Set<PatternFieldValidator> patternValidatorSet;

    @Autowired
    private TypeValidationHandler typeFieldValidatorHandler;

    @Override
    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        for (PatternFieldValidator patternValidator : patternValidatorSet) {
            if (patternValidator.supportsPattern(field, fieldComponentInfo.getPattern())) {
                String errorMessage = patternValidator.validate(fieldComponentInfo, field, label, value);
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
        return typeFieldValidatorHandler;
    }
}
