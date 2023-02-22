package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.FormatFieldValidator;
import cn.maiaimei.framework.swift.validation.validator.typevalidator.CurrencyFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CurrencyAmountFieldValidator implements FormatFieldValidator {

    private static final String FORMAT = "3!a15d";
    private static final String REGEX = "^[A-Z]{3}[0-9]+,([0-9]+)?$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Autowired
    private CurrencyFieldValidator currencyFieldValidator;

    @Override
    public boolean supportsFormat(Field field, String format) {
        return FORMAT.equals(format);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        if (!PATTERN.matcher(value).matches()) {
            return ValidationError.mustMatchFormat(label, FORMAT, value);
        }
        String currency = field.getComponent(1);
        String amount = field.getComponent(2);
        String errorMessage = currencyFieldValidator.validate(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            return errorMessage;
        }
        if (ValidatorUtils.gt(amount, 15)) {
            return ValidationError.mustBeVariableLengthCharacter(label, 15, "d", value);
        }
        // TODO: validate amount by currency
        return null;
    }
}
