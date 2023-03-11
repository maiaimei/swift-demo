package cn.maiaimei.framework.swift.validation.validator.typevalidator;

import cn.maiaimei.framework.swift.model.Currencies;
import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.validator.TypeFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyFieldValidator implements TypeFieldValidator {
    private static final String TYPE = "Currency";

    @Autowired
    private Currencies currencies;

    @Override
    public boolean supportsType(Field field, String type) {
        return StringUtils.equalsIgnoreCase(TYPE, type);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
//        if (currencies.getCurrencies().stream().noneMatch(t -> t.getCurrency().equals(value))) {
//            return label.concat(" unknown currency, invalid value is ".concat(value));
//        }
        return null;
    }
}
