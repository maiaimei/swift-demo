package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class AbstractFieldValidator<T extends Field> implements FieldValidator<T> {
    protected boolean isBlank(Field field, String value) {
        return field == null || StringUtils.isBlank(value);
    }

    protected boolean supports(Map<String, Predicate<String>> predicateMap, String nameOrFormat) {
        for (Map.Entry<String, Predicate<String>> entry : predicateMap.entrySet()) {
            if (entry.getKey().equals(nameOrFormat)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String validate(T field, String format, String value) {
        if (isBlank(field, value)) {
            return ValidationError.mustNotBeBlank(field.getName());
        }
        return doValidate(field, format, value);
    }

    protected abstract String doValidate(T field, String format, String value);

    protected String doValidateFixedCVarX(T field, String format, String value, boolean isOptionalVarX) {
        List<String> componentLabels = field.getComponentLabels();
        String name = field.getName();
        String value1 = field.getComponent(1);
        String value2 = field.getComponent(2);
        return ValidatorUtils.validateFixedCVarX(name, format,
                Arrays.asList(value, value1, value2),
                Arrays.asList(componentLabels.get(0), componentLabels.get(1)),
                isOptionalVarX);
    }
}
