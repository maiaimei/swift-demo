package cn.maiaimei.framework.swift.validation.validator.typevalidator;

import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractTypeValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CurrencyFieldValidator extends AbstractTypeValidator {
    private static final String TYPE = "Currency";

    @Override
    public boolean supportsType(String type) {
        return StringUtils.equalsIgnoreCase(TYPE, type);
    }

    @Override
    public String validate(BaseValidationInfo validationInfo, String label, String value) {
        // TODO: validate currency
        return null;
    }
}
