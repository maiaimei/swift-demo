package cn.maiaimei.framework.swift.validation.validator.patternvalidator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.PatternFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Dates defined as 8!n4!n must be in the form of YYYYMMDDHHMM.
 * Dates defined as 8!n must be in the form of YYYYMMDD.
 * Dates defined as 6!n must be in the form of YYMMDD
 * Times defined as 4!n must be in the form of HHMM
 */
@Component
public class DateTimeFieldValidator implements PatternFieldValidator {

    private static final String VALIDATOR_PATTERN_01 = "<DATE2>[/<DATE2>]";
    private static final String VALIDATOR_PATTERN_01_FORMAT = "6!n[/6!n]";
    private static final String VALIDATOR_PATTERN_01_REGEX = "^[0-9]{6}(/[0-9]{6})?$";
    private static final Pattern VALIDATOR_PATTERN_01_PATTERN = Pattern.compile(VALIDATOR_PATTERN_01_REGEX);
    private static final List<String> VALIDATOR_PATTERN_LIST = Collections.singletonList(
            VALIDATOR_PATTERN_01
    );

    private static final String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";
    private static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    private static final String PATTERN_YYMMDD = "yyMMdd";
    private static final String PATTERN_HHMM = "HHmm";
    private static final List<String> PATTERN_LIST = Arrays.asList(
            PATTERN_YYYYMMDDHHMM,
            PATTERN_YYYYMMDD,
            PATTERN_YYMMDD,
            PATTERN_HHMM
    );

    @Override
    public boolean supportsPattern(Field field, String pattern) {
        if (StringUtils.isNotBlank(pattern)) {
            for (String p : PATTERN_LIST) {
                if (StringUtils.equalsIgnoreCase(p, pattern)) {
                    return true;
                }
            }
        }
        return VALIDATOR_PATTERN_LIST.contains(field.validatorPattern());
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        for (String p : PATTERN_LIST) {
            if (StringUtils.equalsIgnoreCase(p, fieldComponentInfo.getPattern())
                    && !ValidatorUtils.isDatetime(p, value)) {
                return ValidationError.mustMatchFormat(label, p.toUpperCase(), value);
            }
        }
        if (VALIDATOR_PATTERN_01.equals(field.validatorPattern())
                && !VALIDATOR_PATTERN_01_PATTERN.matcher(value).matches()) {
            return ValidationError.mustMatchFormat(label, VALIDATOR_PATTERN_01_FORMAT, value);
        }
        return null;
    }
}
