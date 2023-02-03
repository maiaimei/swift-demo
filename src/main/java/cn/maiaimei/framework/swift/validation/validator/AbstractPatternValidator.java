package cn.maiaimei.framework.swift.validation.validator;

/**
 * Validate field by pattern
 */
public abstract class AbstractPatternValidator implements FieldValidator {
    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsType(String type) {
        return false;
    }
}
