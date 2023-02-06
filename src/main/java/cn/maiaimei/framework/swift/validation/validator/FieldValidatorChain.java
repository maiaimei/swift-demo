package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.config.model.ComponentInfo;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class FieldValidatorChain {
    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;

    @Autowired
    private EnumFieldValidator enumFieldValidator;

    @Autowired
    private Set<AbstractFormatValidator> formatValidatorSet;

    @Autowired
    private Set<AbstractPatternValidator> patternValidatorSet;

    @Autowired
    private Set<AbstractTypeValidator> typeValidatorSet;

    public void doValidation(ValidationResult result, BaseValidationInfo validationInfo, Field field, String label, String value) {
        String errorMessage;
        errorMessage = mandatoryFieldValidator.validate(validationInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        if (StringUtils.isBlank(value)) {
            return;
        }
        errorMessage = validateByFormat(validationInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = validateByPattern(validationInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = validateByType(validationInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = enumFieldValidator.validate(validationInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        if (validationInfo instanceof FieldInfo) {
            validateComponents(result, (FieldInfo) validationInfo, field, label, value);
        }
    }

    private String validateByFormat(BaseValidationInfo validationInfo, Field field, String errorField, String tagValue) {
        for (AbstractFormatValidator formatValidator : formatValidatorSet) {
            if (formatValidator.supportsFormat(field, validationInfo.getFormat())) {
                String errorMessage = formatValidator.validate(validationInfo, field, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private String validateByPattern(BaseValidationInfo validationInfo, Field field, String errorField, String tagValue) {
        for (AbstractPatternValidator patternValidator : patternValidatorSet) {
            if (patternValidator.supportsPattern(field, validationInfo.getPattern())) {
                String errorMessage = patternValidator.validate(validationInfo, field, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private String validateByType(BaseValidationInfo validationInfo, Field field, String errorField, String tagValue) {
        for (AbstractTypeValidator typeValidator : typeValidatorSet) {
            if (typeValidator.supportsType(field, validationInfo.getType())) {
                String errorMessage = typeValidator.validate(validationInfo, field, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private void validateComponents(ValidationResult result, FieldInfo fieldInfo, Field field, String label, String value) {
        List<ComponentInfo> componentInfos = fieldInfo.getComponents();
        if (StringUtils.isBlank(value) || CollectionUtils.isEmpty(componentInfos)) {
            return;
        }
        ValidationResult res = new ValidationResult();
        res.setErrorMessages(new ArrayList<>());
        List<String> components = field.getComponents();
        List<String> componentLabels = field.getComponentLabels();
        for (ComponentInfo componentInfo : componentInfos) {
            int number = componentInfo.getNumber() - 1;
            String componentValue = components.get(number);
            String componentLabel = Optional.ofNullable(componentInfo.getLabel()).orElse(componentLabels.get(number));
            doValidation(res, componentInfo, field, componentLabel, componentValue);
        }
        if (!CollectionUtils.isEmpty(res.getErrorMessages())) {
            String errorMessage = String.join(", ", res.getErrorMessages());
            result.addErrorMessage(label + " error, " + errorMessage + ", original value is " + value);
        }
    }
}
