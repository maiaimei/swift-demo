package cn.maiaimei.framework.swift.validation;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class ValidationError {

    private static final String MUST_BE_FORMAT_A =
            "contain alphabetic, capital letters (A through Z), upper case only";
    private static final String MUST_BE_FORMAT_C =
            "contain alpha-numeric capital letters (upper case), and digits only";
    private static final String MUST_BE_FORMAT_N = "contain numeric, digits (0 through 9) only";
    private static final String MUST_BE_FORMAT_D =
            "including decimal comma ',' preceding the fractional part. The fractional part may be missing, but the decimal comma must always be present";
    private static final String MUST_BE_FORMAT_X = "contain SWIFT X set only";
    private static final String MUST_BE_FORMAT_Z = "contain SWIFT Z set only";
    private static final String MUST_BE_STARTS_WITH_SLASH = "and starts with slash";

    private static final String MUST_BE_PRESENT = "%s must be present";
    private static final String MUST_NOT_BLANK = "%s must not be blank";
    private static final String MUST_BE_FIXED_LENGTH_CHARACTER =
            "%s length must be %s, %s, invalid value is %s";
    private static final String MUST_BE_FIXED_LENGTH_CHARACTER_STARTS_WITH_SLASH =
            "%s length must be %s, %s, %s, invalid value is %s";
    private static final String MUST_BE_VARIABLE_LENGTH_CHARACTER =
            "%s length must be less than or equal to %s, %s, invalid value is %s";
    private static final String MUST_BE_VARIABLE_LENGTH_CHARACTER_STARTS_WITH_SLASH =
            "%s length must be less than or equal to %s, %s, %s, invalid value is %s";
    private static final String MUST_BE_MULTILINE_SWIFT_SET =
            "%s up to %s lines, with a maximum of %s characters per line, contain SWIFT %s set only, invalid value is %s";
    private static final String MUST_IN_OPTIONS = "%s must in \"%s\", invalid value is %s";
    private static final String MUST_MATCH_FORMAT = "%s must match %s, invalid value is %s";

    private ValidationError() {
        throw new UnsupportedOperationException();
    }

    public static String mustBePresent(String label) {
        return String.format(MUST_BE_PRESENT, label);
    }

    public static String mustNotBlank(String label) {
        return String.format(MUST_NOT_BLANK, label);
    }

    public static String mustBeFixedLengthCharacter(
            String label, int length, String type, String value) {
        return String.format(
                MUST_BE_FIXED_LENGTH_CHARACTER, label, length, getErrorMessage(type), value);
    }

    public static String mustBeFixedLengthCharacterStartsWithSlash(
            String label, int length, String type, String value) {
        return String.format(
                MUST_BE_FIXED_LENGTH_CHARACTER_STARTS_WITH_SLASH,
                label,
                length,
                getErrorMessage(type),
                MUST_BE_STARTS_WITH_SLASH,
                value);
    }

    public static String mustBeVariableLengthCharacter(
            String label, int maxlength, String type, String value) {
        return String.format(
                MUST_BE_VARIABLE_LENGTH_CHARACTER, label, maxlength, getErrorMessage(type), value);
    }

    public static String mustBeVariableLengthCharacterStartsWithSlash(
            String label, int maxlength, String type, String value) {
        return String.format(
                MUST_BE_VARIABLE_LENGTH_CHARACTER_STARTS_WITH_SLASH,
                label,
                maxlength,
                getErrorMessage(type),
                MUST_BE_STARTS_WITH_SLASH,
                value);
    }

    public static String mustBeMultilineSwiftSet(
            String label, int rowcount, int length, String type, String value) {
        return String.format(MUST_BE_MULTILINE_SWIFT_SET, label, rowcount, length, type, value);
    }

    public static String mustInOptions(String label, String value, List<String> options) {
        return String.format(MUST_IN_OPTIONS, label, String.join("|", options), value);
    }

    public static String mustMatchFormat(String label, String format, String value) {
        return String.format(MUST_MATCH_FORMAT, label, format, value);
    }

    private static String getErrorMessage(String type) {
        switch (type) {
            case ValidationConstants.FORMAT_A:
                return MUST_BE_FORMAT_A;
            case ValidationConstants.FORMAT_C:
                return MUST_BE_FORMAT_C;
            case ValidationConstants.FORMAT_N:
                return MUST_BE_FORMAT_N;
            case ValidationConstants.FORMAT_D:
                return MUST_BE_FORMAT_D;
            case ValidationConstants.FORMAT_X:
                return MUST_BE_FORMAT_X;
            case ValidationConstants.FORMAT_Z:
                return MUST_BE_FORMAT_Z;
            default:
                return StringUtils.EMPTY;
        }
    }
}
