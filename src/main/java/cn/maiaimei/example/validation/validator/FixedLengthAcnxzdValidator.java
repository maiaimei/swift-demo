package cn.maiaimei.example.validation.validator;

import cn.maiaimei.example.validation.ValidatorUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class FixedLengthAcnxzdValidator implements SwiftValidator {
    @Override
    public boolean supportValidate(String format) {
        return Pattern.matches(ValidatorUtils.FIXED_LENGTH_ACNXZD, format);
    }

    @Override
    public boolean doValidate(String format, String value) {
        int length = ValidatorUtils.getNumber(format);
        String type = ValidatorUtils.getType(format);
        return ValidatorUtils.eq(value, length) && ValidatorUtils.containsOnly(value, type);
    }
}
