package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Validate field by enum
 */
@Component
public class EnumFieldValidator implements FieldValidator, FieldValidatorHandler {

    @Autowired
    private ComponentValidator componentValidator;

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        List<String> options = fieldComponentInfo.getOptions();
        if (!CollectionUtils.isEmpty(options) && !options.contains(value)) {
            return ValidationError.mustInOptions(label, value, options);
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
        handleNextValidation(result, fieldComponentInfo, field, label, value);
    }

    @Override
    public FieldValidatorHandler getNextValidationHandler() {
        return componentValidator;
    }
}
