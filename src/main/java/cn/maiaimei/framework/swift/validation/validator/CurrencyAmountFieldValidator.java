package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.model.BaseValidationInfo;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CurrencyAmountFieldValidator implements FieldValidator {

    private static final String FORMAT = "3!a15d";
    private static final String REGEX = "^[A-Z]{3}[0-9]+,([0-9]+)?$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final String VALIDATOR_PATTERN = "<CUR><AMOUNT>15";

    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(String pattern) {
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
    public void validate(ValidationResult result, Field field, FieldInfo fieldInfo, String label, String value) {
        if (!VALIDATOR_PATTERN.equals(field.validatorPattern())) {
            return;
        }
        if (!PATTERN.matcher(value).matches()) {
            result.addErrorMessage(ValidationError.mustMatchFormat(label, FORMAT, value));
            return;
        }
        String currency = field.getComponent(1);
        String amount = field.getComponent(2);
        if (ValidatorUtils.gt(amount, 15)) {
            result.addErrorMessage(ValidationError.mustBeVariableLengthCharacter(label, 15, "d", value));
            return;
        }
        // TODO: validate currency
        // TODO: validate amount by currency
    }
}
