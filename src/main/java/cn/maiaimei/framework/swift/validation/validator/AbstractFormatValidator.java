package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;

/**
 * Validate field by format
 */
public abstract class AbstractFormatValidator implements FieldValidator {

    @Override
    public boolean supportsPattern(String pattern, Field field) {
        return false;
    }

    @Override
    public boolean supportsType(String type) {
        return false;
    }

    protected int getLength(String format) {
        return ValidatorUtils.getNumber(format);
    }

    protected String getType(String format) {
        return ValidatorUtils.getType(format);
    }

    protected String trimStartSlash(String format, String value) {
        if (ValidatorUtils.isStartsWithSlash(format) && ValidatorUtils.isStartsWithSlash(value)) {
            return value.substring(1);
        }
        return value;
    }

}
