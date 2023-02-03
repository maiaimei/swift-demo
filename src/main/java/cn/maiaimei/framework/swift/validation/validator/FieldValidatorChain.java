package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void doValidation(ValidationResult result, BaseValidationInfo validationInfo, String label, String value) {
        String errorMessage;
        errorMessage = mandatoryFieldValidator.validate(validationInfo, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        if (StringUtils.isBlank(value)) {
            return;
        }
        errorMessage = validateByFormat(validationInfo, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = validateByPattern(validationInfo, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = validateByType(validationInfo, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = enumFieldValidator.validate(validationInfo, label, value);
        result.addErrorMessage(errorMessage);
    }

    private String validateByFormat(BaseValidationInfo validationInfo, String errorField, String tagValue) {
        for (AbstractFormatValidator formatValidator : formatValidatorSet) {
            if (formatValidator.supportsFormat(validationInfo.getFormat())) {
                String errorMessage = formatValidator.validate(validationInfo, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private String validateByPattern(BaseValidationInfo validationInfo, String errorField, String tagValue) {
        for (AbstractPatternValidator patternValidator : patternValidatorSet) {
            if (patternValidator.supportsPattern(validationInfo.getPattern())) {
                String errorMessage = patternValidator.validate(validationInfo, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private String validateByType(BaseValidationInfo validationInfo, String errorField, String tagValue) {
        for (AbstractTypeValidator typeValidator : typeValidatorSet) {
            if (typeValidator.supportsType(validationInfo.getType())) {
                String errorMessage = typeValidator.validate(validationInfo, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }
}
