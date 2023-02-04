package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
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
import java.util.regex.Pattern;

@Component
public class MultipleComponentsFieldValidator implements FieldValidator {

    private static final String YYYYMMDD_HHMM_FORMAT = "8!n4!n";
    private static final String YYYYMMDD_HHMM_REGEX = "^[0-9]{12}$";
    private static final Pattern YYYYMMDD_HHMM_PATTERN = Pattern.compile(YYYYMMDD_HHMM_REGEX);

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(String pattern, Field field) {
        return false;
    }

    @Override
    public boolean supportsType(String type) {
        return false;
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        return null;
    }

    @Override
    public void validate(ValidationResult result, FieldInfo fieldInfo, Field field, String label, String value) {
        List<ComponentInfo> componentInfos = fieldInfo.getComponents();
        if (StringUtils.isBlank(value) || CollectionUtils.isEmpty(componentInfos)) {
            return;
        }
        String errorMessage = validateFormat(fieldInfo.getFormat(), label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            result.addErrorMessage(errorMessage);
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
            fieldValidatorChain.doValidation(res, componentInfo, field, componentLabel, componentValue);
        }
        if (!CollectionUtils.isEmpty(res.getErrorMessages())) {
            errorMessage = String.join(", ", res.getErrorMessages());
            result.addErrorMessage(label + " error, " + errorMessage + ", original value is " + value);
        }
    }

    private String validateFormat(String format, String label, String value) {
        if (YYYYMMDD_HHMM_FORMAT.equals(format) && !YYYYMMDD_HHMM_PATTERN.matcher(value).matches()) {
            return ValidationError.mustMatchFormat(label, YYYYMMDD_HHMM_FORMAT, value);
        }
        return null;
    }
}
