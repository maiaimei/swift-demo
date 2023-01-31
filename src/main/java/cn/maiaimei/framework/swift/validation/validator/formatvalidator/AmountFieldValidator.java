package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class AmountFieldValidator<T extends Field> extends AbstractFormatValidator<T> {
    /**
     * 12d
     */
    private static final String AMOUNT_12 = "<AMOUNT>12";

    /**
     * 3!a15d
     * (Currency)(Amount)
     */
    private static final String CUR_AMOUNT_15 = "<CUR><AMOUNT>15";

    private static final Map<String, Predicate<String>> FORMAT_VALIDATE_MAP = new HashMap<>();
    private static final Map<String, String> FORMAT_MAP = new HashMap<>();

    static {
        FORMAT_VALIDATE_MAP.put(AMOUNT_12, ValidatorUtils::isMatchAmount12);
        FORMAT_VALIDATE_MAP.put(CUR_AMOUNT_15, ValidatorUtils::isMatchCurrencyAmount15);
        FORMAT_MAP.put(AMOUNT_12, "12d");
        FORMAT_MAP.put(CUR_AMOUNT_15, "3!a15d");
    }

    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isSupportsFormat(FORMAT_VALIDATE_MAP, format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        if (!FORMAT_VALIDATE_MAP.get(format).test(value)) {
            return ValidationError.mustMatchFormat(tag, FORMAT_MAP.get(format), value);
        }
        return null;
    }
}
