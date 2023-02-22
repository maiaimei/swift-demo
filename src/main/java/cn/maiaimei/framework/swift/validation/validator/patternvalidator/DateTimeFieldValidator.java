package cn.maiaimei.framework.swift.validation.validator.patternvalidator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.PatternFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DateTimeFieldValidator implements PatternFieldValidator {

    /**
     * Dates defined as 8!n4!n must be in the form of YYYYMMDDHHMM.
     */
    private static final String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";

    /**
     * Dates defined as 8!n must be in the form of YYYYMMDD.
     */
    private static final String PATTERN_YYYYMMDD = "yyyyMMdd";

    /**
     * Dates defined as 6!n must be in the form of YYMMDD
     */
    private static final String PATTERN_YYMMDD = "yyMMdd";

    /**
     * Times defined as 4!n must be in the form of HHMM
     */
    private static final String PATTERN_HHMM = "HHmm";

    private static final List<String> PATTERN_LIST = Arrays.asList(
            PATTERN_YYYYMMDDHHMM,
            PATTERN_YYYYMMDD,
            PATTERN_YYMMDD,
            PATTERN_HHMM
    );

    @Override
    public boolean supportsPattern(Field field, String pattern) {
        return isMatchPattern(pattern);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        for (String p : PATTERN_LIST) {
            if (StringUtils.equalsIgnoreCase(p, fieldComponentInfo.getPattern())
                    && !ValidatorUtils.isDatetime(p, value)) {
                return ValidationError.mustMatchFormat(label, p.toUpperCase(), value);
            }
        }
        return null;
    }

    private boolean isMatchPattern(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return false;
        }
        for (String p : PATTERN_LIST) {
            if (StringUtils.equalsIgnoreCase(p, pattern)) {
                return true;
            }
        }
        return false;
    }
}
