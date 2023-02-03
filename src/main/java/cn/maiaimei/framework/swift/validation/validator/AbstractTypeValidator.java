package cn.maiaimei.framework.swift.validation.validator;

public abstract class AbstractTypeValidator implements FieldValidator {
    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsPattern(String pattern) {
        return false;
    }
}
