package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * validate fixed length alpha-numeric and optional Swift X set, Swift X set start with slash, e.g. 4!c[/35x]
 */
@Component
public class FixedCVarXValidatorStrategy<T extends Field> implements FormatValidatorStrategy<T> {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchFixedCVarXFormat(format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        List<String> components = field.getComponents();
        List<String> componentLabels = field.getComponentLabels();
        boolean isOptionalVarX = ValidatorUtils.isOptionalFormat(ValidatorUtils.VAR_X_FORMAT_PATTERN, format, 0);
        return ValidatorUtils.validateFixedCVarX(tag, format, components, componentLabels, isOptionalVarX);
    }
}
