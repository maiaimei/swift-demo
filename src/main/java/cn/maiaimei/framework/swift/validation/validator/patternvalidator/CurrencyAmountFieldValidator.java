package cn.maiaimei.framework.swift.validation.validator.patternvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.validator.AbstractPatternValidator;
import cn.maiaimei.framework.swift.validation.validator.typevalidator.CurrencyFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CurrencyAmountFieldValidator extends AbstractPatternValidator {

    private static final String FORMAT = "3!a15d";
    private static final String REGEX = "^[A-Z]{3}[0-9]+,([0-9]+)?$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final String VALIDATOR_PATTERN = "<CUR><AMOUNT>15";

    @Autowired
    private CurrencyFieldValidator currencyFieldValidator;

    @Override
    public boolean supportsPattern(Field field, String pattern) {
        return VALIDATOR_PATTERN.equals(field.validatorPattern());
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        if (!PATTERN.matcher(value).matches()) {
            return ValidationError.mustMatchFormat(label, FORMAT, value);
        }
        String currency = field.getComponent(1);
        String amount = field.getComponent(2);
        if (ValidatorUtils.gt(amount, 15)) {
            return ValidationError.mustBeVariableLengthCharacter(label, 15, "d", value);
        }
        String errorMessage = currencyFieldValidator.validate(fieldComponentInfo, field, label, value);
        if (StringUtils.isNotBlank(errorMessage)) {
            return errorMessage;
        }
        // TODO: validate amount by currency
        return null;
    }
}
