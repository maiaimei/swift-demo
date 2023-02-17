package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.ComponentInfo;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.config.FieldInfo;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FieldValidatorChain {
    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;

    @Autowired
    private EnumFieldValidator enumFieldValidator;

    @Autowired
    private Set<FormatFieldValidator> formatValidatorSet;

    @Autowired
    private Set<PatternFieldValidator> patternValidatorSet;

    @Autowired
    private Set<TypeFieldValidator> typeValidatorSet;

    public void doValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        doValidationInternal(result, fieldComponentInfo, field, label, value);
        if (fieldComponentInfo instanceof FieldInfo) {
            validateComponents(result, (FieldInfo) fieldComponentInfo, field, label, value);
        }
    }

    private void doValidationInternal(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String errorMessage;
        errorMessage = mandatoryFieldValidator.validate(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        if (StringUtils.isBlank(value)) {
            return;
        }
        errorMessage = validateByFormat(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = validateByPattern(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = validateByType(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
            return;
        }
        errorMessage = enumFieldValidator.validate(fieldComponentInfo, field, label, value);
        result.addErrorMessage(errorMessage);
    }

    private String validateByFormat(FieldComponentInfo validationInfo, Field field, String errorField, String tagValue) {
        for (FormatFieldValidator formatValidator : formatValidatorSet) {
            if (formatValidator.supportsFormat(field, validationInfo.getFormat())) {
                String errorMessage = formatValidator.validate(validationInfo, field, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private String validateByPattern(FieldComponentInfo validationInfo, Field field, String errorField, String tagValue) {
        for (PatternFieldValidator patternValidator : patternValidatorSet) {
            if (patternValidator.supportsPattern(field, validationInfo.getPattern())) {
                String errorMessage = patternValidator.validate(validationInfo, field, errorField, tagValue);
                if (StringUtils.isNotBlank(errorMessage)) {
                    return errorMessage;
                }
            }
        }
        return null;
    }

    private String validateByType(FieldComponentInfo validationInfo, Field field, String errorField, String tagValue) {
        for (TypeFieldValidator typeValidator : typeValidatorSet) {
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
                String componentLabel = StringUtils.isNotBlank(componentInfo.getLabel()) ? componentInfo.getLabel() : componentLabels.get(index);
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
