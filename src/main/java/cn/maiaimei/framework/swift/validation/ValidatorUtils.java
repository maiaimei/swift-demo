package cn.maiaimei.framework.swift.validation;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public final class ValidatorUtils {

    public static boolean eq(String value, int length) {
        return value.length() == length;
    }

    public static boolean ne(String value, int length) {
        return value.length() != length;
    }

    public static boolean le(String value, int length) {
        return value.length() <= length;
    }

    public static boolean lt(String value, int length) {
        return value.length() < length;
    }

    public static boolean gt(String value, int length) {
        return value.length() > length;
    }

    public static boolean containsOnlyUpperCase(String value) {
        return StringUtils.containsOnly(value, ValidationConstants.ALPHABETIC);
    }

    public static boolean containsOnlyAlphaNumeric(String value) {
        return StringUtils.containsOnly(value, ValidationConstants.ALPHA_NUMERIC);
    }

    public static boolean containsOnlyNumeric(String value) {
        return StringUtils.containsOnly(value, ValidationConstants.NUMERIC);
    }

    public static boolean containsOnlySwiftXSet(String value) {
        return StringUtils.containsOnly(value, ValidationConstants.SWIFT_X_SET);
    }

    public static boolean containsOnlySwiftZSet(String value) {
        return StringUtils.containsOnly(value, ValidationConstants.SWIFT_Z_SET);
    }

    public static boolean containsOnlyDecimals(String value) {
        return StringUtils.containsOnly(value, ValidationConstants.DECIMALS);
    }

    public static boolean containsOnly(String value, String type) {
        switch (type) {
            case ValidationConstants.FORMAT_A:
                return containsOnlyUpperCase(value);
            case ValidationConstants.FORMAT_C:
                return containsOnlyAlphaNumeric(value);
            case ValidationConstants.FORMAT_N:
                return containsOnlyNumeric(value);
            case ValidationConstants.FORMAT_X:
                return containsOnlySwiftXSet(value);
            case ValidationConstants.FORMAT_Z:
                return containsOnlySwiftZSet(value);
            case ValidationConstants.FORMAT_D:
                return containsOnlyDecimals(value);
            default:
                return false;
        }
    }

    public static boolean containsOther(String value, String type) {
        return !containsOnly(value, type);
    }

    public static boolean isFixedLengthCharacter(String format) {
        return ValidationConstants.FIXED_LENGTH_CHARACTER_PATTERN.matcher(format).matches();
    }

    public static boolean isFixedLengthCharacterStartsWithSlash(String format) {
        return ValidationConstants.FIXED_LENGTH_CHARACTER_STARTS_WITH_SLASH_PATTERN.matcher(format).matches();
    }

    public static boolean isVariableLengthCharacter(String format) {
        return ValidationConstants.VARIABLE_LENGTH_CHARACTER_PATTERN.matcher(format).matches();
    }

    public static boolean isVariableLengthCharacterStartsWithSlash(String format) {
        return ValidationConstants.VARIABLE_LENGTH_CHARACTER_STARTS_WITH_SLASH_PATTERN.matcher(format).matches();
    }

    public static boolean isMultilineSwiftSet(String format) {
        return ValidationConstants.MULTILINE_SWIFT_SET_PATTERN.matcher(format).matches();
    }

    public static boolean isDatetime(String pattern, String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isStartsWithSlash(String value) {
        return value.startsWith(ValidationConstants.SLASH);
    }

    public static boolean isMandatory(String status) {
        return ValidationConstants.MANDATORY.equals(status);
    }

    public static boolean isRequired(String status) {
        return ValidationConstants.REQUIRED_FLAG.contains(status);
    }

    public static List<Integer> getNumbers(String format) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = ValidationConstants.NUMBER_PATTERN.matcher(format);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(0)));
        }
        return numbers;
    }

    public static int getNumber(String format) {
        List<Integer> numbers = getNumbers(format);
        return numbers.get(0);
    }

    public static String getType(String format) {
        Matcher matcher = ValidationConstants.FORMAT_PATTERN.matcher(format);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return StringUtils.EMPTY;
    }

    public static String trimStartSlash(String format, String value) {
        if (ValidatorUtils.isStartsWithSlash(format) && ValidatorUtils.isStartsWithSlash(value)) {
            return value.substring(1);
        }
        return value;
    }

    private ValidatorUtils() {
        throw new UnsupportedOperationException();
    }
}
