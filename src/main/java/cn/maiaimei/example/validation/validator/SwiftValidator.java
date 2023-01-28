package cn.maiaimei.example.validation.validator;

public interface SwiftValidator {
    boolean supportValidate(String format);

    boolean doValidate(String format, String value);
}
