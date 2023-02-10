package cn.maiaimei.framework.swift.validation.validator.typevalidator;

import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractTypeValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CurrencyFieldValidator extends AbstractTypeValidator {
    private static final String TYPE = "Currency";

    @Override
    public boolean supportsType(Field field, String type) {
        return StringUtils.equalsIgnoreCase(TYPE, type);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        // TODO: validate currency
        return null;
    }
}
