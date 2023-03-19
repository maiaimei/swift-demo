package cn.maiaimei.framework.swift.validation;

import java.util.regex.Pattern;

public final class ValidationConstants {

    public static final String SLASH = "/";
    public static final String MANDATORY = "M";

    /** a, alphabetic, capital letters (A through Z), upper case only */
    public static final char[] ALPHABETIC =
            new char[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'
            };

    /** c, alpha-numeric capital letters (upper case), and digits only */
    public static final char[] ALPHA_NUMERIC =
            new char[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            };

    /** n, numeric, digits (0 through 9) only */
    public static final char[] NUMERIC =
            new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /** x, SWIFT X set: • A to Z • a to z • 0 to 9 / - ? : ( ) . , ’ + SPACE CrLf */
    public static final char[] SWIFT_X_SET =
            new char[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', '-', '?', ':', '(', ')',
                '.', ',', '’', '+', ' ', '\r', '\n'
            };

    /**
     * z, SWIFT Z set: • A to Z • a to z • 0 to 9 / - ? : ( ) . , ’ + SPACE CrLf = ! “ % & * < > ; @
     * # _ {
     */
    public static final char[] SWIFT_Z_SET =
            new char[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', '-', '?', ':', '(', ')',
                '.', ',', '’', '+', ' ', '\r', '\n', '=', '!', '“', '%', '&', '*', '<', '>', ';',
                '@', '#', '_', '{'
            };

    /**
     * d, decimals, including decimal comma ',' preceding the fractional part. The fractional part
     * may be missing, but the decimal comma must always be present
     */
    public static final char[] DECIMALS =
            new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ','};

    /** a: alphabetic */
    public static final String FORMAT_A = "a";

    /** c: alpha-numeric */
    public static final String FORMAT_C = "c";

    /** n: numeric */
    public static final String FORMAT_N = "n";

    /** x: SWIFT X set */
    public static final String FORMAT_X = "x";

    /** z: SWIFT Z set */
    public static final String FORMAT_Z = "z";

    /** d: decimals */
    public static final String FORMAT_D = "d";

    /**
     * format
     *
     * <p>a: alphabetic
     *
     * <p>c: alpha-numeric
     *
     * <p>n: numeric
     *
     * <p>x: SWIFT X set
     *
     * <p>z: SWIFT Z set
     *
     * <p>d: decimals
     */
    public static final String FORMAT_REGEX = "[acnxzd]";

    public static final String NUMBER_REGEX = "\\d+";

    /**
     * fixed length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals,
     * e.g.
     *
     * <p>16!x, must be 16 characters
     *
     * <p>x can be replaced by a|c|n|z|d
     */
    public static final String FIXED_LENGTH_CHARACTER_REGEX = "^[1-9]{1}[0-9]*![acnxzd]$";

    /**
     * fixed length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, and
     * starts with slash, e.g.
     *
     * <p>/16!x, must be 16 characters and starts with slash
     *
     * <p>x can be replaced by a|c|n|z|d
     */
    public static final String FIXED_LENGTH_CHARACTER_STARTS_WITH_SLASH_REGEX =
            "^/[1-9]{1}[0-9]*![acnxzd]$";

    /**
     * variable length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals,
     * e.g.
     *
     * <p>16x, with a maximum of 16 characters
     *
     * <p>x can be replaced by a|c|n|z|d
     */
    public static final String VARIABLE_LENGTH_CHARACTER_REGEX = "^[1-9]{1}[0-9]*[acnxzd]$";

    /**
     * variable length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals,
     * and starts with slash, e.g.
     *
     * <p>/16x, with a maximum of 16 characters and starts with slash
     *
     * <p>x can be replaced by a|c|n|z|d
     */
    public static final String VARIABLE_LENGTH_CHARACTER_STARTS_WITH_SLASH_REGEX =
            "^/[1-9]{1}[0-9]*[acnxzd]$";

    /**
     * multi line SWIFT X set or SWIFT Z set, e.g.
     *
     * <p>4*35x, up to 4 lines, with a maximum of 35 characters per line, SWIFT X set only
     *
     * <p>50*65z, up to 50 lines, with a maximum of 65 characters per line, SWIFT Z set only
     */
    public static final String MULTILINE_SWIFT_SET_REGEX = "^[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*[xz]$";

    public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    public static final Pattern FORMAT_PATTERN = Pattern.compile(FORMAT_REGEX);
    public static final Pattern FIXED_LENGTH_CHARACTER_PATTERN =
            Pattern.compile(FIXED_LENGTH_CHARACTER_REGEX);
    public static final Pattern FIXED_LENGTH_CHARACTER_STARTS_WITH_SLASH_PATTERN =
            Pattern.compile(FIXED_LENGTH_CHARACTER_STARTS_WITH_SLASH_REGEX);
    public static final Pattern VARIABLE_LENGTH_CHARACTER_PATTERN =
            Pattern.compile(VARIABLE_LENGTH_CHARACTER_REGEX);
    public static final Pattern VARIABLE_LENGTH_CHARACTER_STARTS_WITH_SLASH_PATTERN =
            Pattern.compile(VARIABLE_LENGTH_CHARACTER_STARTS_WITH_SLASH_REGEX);
    public static final Pattern MULTILINE_SWIFT_SET_PATTERN =
            Pattern.compile(MULTILINE_SWIFT_SET_REGEX);

    private ValidationConstants() {
        throw new UnsupportedOperationException();
    }
}
