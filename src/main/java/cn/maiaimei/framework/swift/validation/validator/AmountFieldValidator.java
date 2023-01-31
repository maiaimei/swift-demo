package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class AmountFieldValidator<T extends Field> extends AbstractFieldValidator<T> {
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
    public boolean supports(String nameOrFormat) {
        return supports(FORMAT_VALIDATE_MAP, nameOrFormat);
    }

    @Override
    public String doValidate(T field, String format, String value) {
        String name = field.getName();
        if (!FORMAT_VALIDATE_MAP.get(format).test(value)) {
            return ValidationError.mustMatchFormat(name, FORMAT_MAP.get(format), value);
        }
        return null;
    }
}
