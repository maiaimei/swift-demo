package cn.maiaimei.framework.swift.validation;

import java.util.List;

public final class ValidationError {
    private static final String MUST_BE_FORMAT_A = "contain alphabetic, capital letters (A through Z), upper case only";
    private static final String MUST_BE_FORMAT_C = "contain alpha-numeric capital letters (upper case), and digits only";
    private static final String MUST_BE_FORMAT_N = "contain numeric, digits (0 through 9) only";
    private static final String MUST_BE_FORMAT_D = "including decimal comma ',' preceding the fractional part. The fractional part may be missing, but the decimal comma must always be present.";
    private static final String MUST_BE_FORMAT_X = "contain SWIFT X set only";
    private static final String MUST_BE_FORMAT_Z = "contain SWIFT Z set only";

    private static final String MUST_BE_PRESENT = "%s must be present";
    private static final String MUST_NOT_BE_BLANK = "%s must not be blank";
    private static final String MUST_BE_FIXED_LENGTH = "%s length must be %s, %s, invalid value is %s";
    private static final String MUST_BE_VAR_LENGTH = "%s length must be less than or equal to %s, %s, invalid value is %s";
    private static final String MUST_BE_MULTILINE_SWIFT_SET = "%s up to %s lines, with a maximum of %s characters per line, contain SWIFT %s set only, invalid value is %s";
    private static final String MUST_MATCH_FORMAT = "%s must match %s, invalid value is %s";
    private static final String MUST_IN_ENUM = "%s must in \"%s\", invalid value is %s";

    private ValidationError() {
        throw new UnsupportedOperationException();
    }

    public static String mustBePresent(String name) {
        return String.format(MUST_BE_PRESENT, name);
    }

    public static String mustNotBeBlank(String name) {
        return String.format(MUST_NOT_BE_BLANK, name);
    }

    public static String mustBeFixedLength(String type, String name, int length, String value) {
        return String.format(MUST_BE_FIXED_LENGTH, name, length, getMessage(type), value);
    }

    public static String mustBeVarLength(String type, String name, int maxlength, String value) {
        return String.format(MUST_BE_VAR_LENGTH, name, maxlength, getMessage(type), value);
    }

    public static String mustBeMultilineSwiftSet(String type, String name, int rowcount, int maxlength, String value) {
        return String.format(MUST_BE_MULTILINE_SWIFT_SET, name, rowcount, maxlength, type, value);
    }

    public static String mustMatchFormat(String name, String format, String value) {
        return String.format(MUST_MATCH_FORMAT, name, format, value);
    }

    public static String mustInEnum(String name, String value, List<String> enumItems) {
        return String.format(MUST_IN_ENUM, name, String.join(",", enumItems), value);
    }

    private static String getMessage(String type) {
        switch (type) {
            case "a":
                return MUST_BE_FORMAT_A;
            case "c":
                return MUST_BE_FORMAT_C;
            case "n":
                return MUST_BE_FORMAT_N;
            case "d":
                return MUST_BE_FORMAT_D;
            case "x":
                return MUST_BE_FORMAT_X;
            case "z":
                return MUST_BE_FORMAT_Z;
            default:
                return "";
        }
    }
}
