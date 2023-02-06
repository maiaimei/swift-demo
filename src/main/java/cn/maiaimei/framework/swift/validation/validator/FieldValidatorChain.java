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
        doValidationInternal(result, validationInfo, field, label, value);
        if (validationInfo instanceof FieldInfo) {
            validateComponents(result, (FieldInfo) validationInfo, field, label, value);
        }
    }

    private void doValidationInternal(ValidationResult result, BaseValidationInfo validationInfo, Field field, String label, String value) {
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
        result.addErrorMessage(errorMessage);
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
            if (componentInfo.getIndex() != null) {
                int index = componentInfo.getIndex() - 1;
                String componentLabel = Optional.ofNullable(componentInfo.getLabel()).orElse(componentLabels.get(index));
                String componentValue = components.get(index);
                doValidationInternal(res, componentInfo, field, componentLabel, componentValue);
            } else if (componentInfo.getStartIndex() != null && componentInfo.getEndIndex() != null) {
                for (int i = componentInfo.getStartIndex(); i <= componentInfo.getEndIndex(); i++) {
                    int index = i - 1;
                    String componentLabel = componentLabels.get(index);
                    String componentValue = components.get(index);
                    doValidationInternal(res, componentInfo, field, componentLabel, componentValue);
                }
            } else {
                result.addErrorMessage(label + " component config error, please check each component index");
            }
        }
        if (!CollectionUtils.isEmpty(res.getErrorMessages())) {
            String errorMessage = String.join(", ", res.getErrorMessages());
            result.addErrorMessage(label + " error, " + errorMessage + ", original value is " + value);
        }
    }
}
