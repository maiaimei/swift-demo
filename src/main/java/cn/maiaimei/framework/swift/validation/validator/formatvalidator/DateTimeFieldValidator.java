package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Dates defined as 6!n must be in the form of YYMMDD.
 * Dates defined as 8!n must be in the form of YYYYMMDD.
 */
@Component
public class DateTimeFieldValidator<T extends Field> extends AbstractFormatValidator<T> {
    /**
     * 8!n4!n
     */
    private static final String DATE4_HHMM = "<DATE4><HHMM>";

    /**
     * 6!n[/6!n]
     */
    private static final String DATE2_OPTIONAL_DATE2 = "<DATE2>[/<DATE2>]";

    private static final Map<String, Predicate<String>> FORMAT_VALIDATE_MAP = new HashMap<>();
    private static final Map<String, String> FORMAT_MAP = new HashMap<>();

    static {
        FORMAT_VALIDATE_MAP.put(DATE4_HHMM, ValidatorUtils::isMatchDate4Hhmm);
        FORMAT_VALIDATE_MAP.put(DATE2_OPTIONAL_DATE2, ValidatorUtils::isMatchDate2OptionalDate2);
        FORMAT_MAP.put(DATE4_HHMM, "yyyyMMddHHmm");
        FORMAT_MAP.put(DATE2_OPTIONAL_DATE2, "yyMMdd");
    }

    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isSupportsFormat(FORMAT_VALIDATE_MAP, format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        String pattern = FORMAT_MAP.get(format);
        if (!FORMAT_VALIDATE_MAP.get(format).test(value) || !ValidatorUtils.validateDatetime(value, pattern)) {
            return ValidationError.mustMatchFormat(tag, pattern, value);
        }
        return null;
    }
}
