package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TypeFieldValidatorComposite implements FieldValidatorHandler {
    @Autowired
    private EnumFieldValidator enumFieldValidator;

    @Autowired
    private Set<TypeFieldValidator> typeValidatorSet;

    @Override
    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        for (TypeFieldValidator typeValidator : typeValidatorSet) {
            if (typeValidator.supportsType(field, fieldComponentInfo.getType())) {
                String errorMessage = typeValidator.validate(fieldComponentInfo, field, label, value);
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
    public FieldValidatorHandler getNextValidationHandler() {
        return enumFieldValidator;
    }
}
