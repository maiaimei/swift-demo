package cn.maiaimei.framework.swift.validation.validator.typevalidator;

import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractTypeValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * TODO:  2 alphabetic, ISO 639 Language Code, e.g., en = English, fr = French, de = German.
 */
@Component
public class ISO639LanguageCodeValidator extends AbstractTypeValidator {
    private static final String LANGUAGE = "Language";

    @Override
    public boolean supportsType(String type) {
        return StringUtils.equalsIgnoreCase(LANGUAGE, type);
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        return null;
    }
}
