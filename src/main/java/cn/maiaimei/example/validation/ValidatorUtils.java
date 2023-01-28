package cn.maiaimei.example.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验器工具类
 */
@Slf4j
public class ValidatorUtils {
    /**
     * alphabetic, capital letters (A through Z), upper case only
     */
    private static final char[] ALPHABETIC = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * alpha-numeric capital letters (upper case), and digits only
     */
    private static final char[] ALPHA_NUMERIC = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * numeric, digits (0 through 9) only
     */
    private static final char[] NUMERIC = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * SWIFT X set:
     * • A to Z
     * • a to z
     * • 0 to 9
     * / - ? : ( ) . , ’ + SPACE CrLf
     */
    private static final char[] SWIFT_X_SET = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '/', '-', '?', ':', '(', ')', '.', ',', '’', '+', ' ', '\r', '\n'
    };

    /**
     * SWIFT Z set:
     * • A to Z
     * • a to z
     * • 0 to 9
     * / - ? : ( ) . , ’ + SPACE CrLf
     * = ! “ % & * < > ; @ # _ {
     */
    private static final char[] SWIFT_Z_SET = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '/', '-', '?', ':', '(', ')', '.', ',', '’', '+', ' ', '\r', '\n',
            '=', '!', '“', '%', '&', '*', '<', '>', ';', '@', '#', '_', '{'
    };

    /**
     * decimals, including decimal comma ',' preceding
     * the fractional part. The fractional part may be
     * missing, but the decimal comma must always be
     * present
     */
    private static final char[] DECIMALS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ','
    };

    /**
     * variable length alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, e.g. 16x
     */
    public static final String VARIABLE_LENGTH_ACNXZD = "[1-9]{1}[0-9]*[acnxzd]";

    /**
     * fixed length alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, e.g. 5!c
     */
    public static final String FIXED_LENGTH_ACNXZD = "[1-9]{1}[0-9]*![acnxzd]";

    /**
     * multiline SWIFT X set or SWIFT Z set, e.g. 4*35x
     */
    public static final String MULTILINE_SWIFT_XZ_SET = "[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*[xz]";

    public static boolean eq(String value, int length) {
        return value.length() == length;
    }

    public static boolean le(String value, int maxlength) {
        return value.length() <= maxlength;
    }
    
    public static boolean containsOnlyUpperCase(String value) {
        return StringUtils.containsOnly(value, ALPHABETIC);
    }

    public static boolean containsOnlyAlphaNumeric(String value) {
        return StringUtils.containsOnly(value, ALPHA_NUMERIC);
    }

    public static boolean containsOnlyNumeric(String value) {
        return StringUtils.containsOnly(value, NUMERIC);
    }

    public static boolean containsOnlySwiftXSet(String value) {
        return StringUtils.containsOnly(value, SWIFT_X_SET);
    }

    public static boolean containsOnlySwiftZSet(String value) {
        return StringUtils.containsOnly(value, SWIFT_Z_SET);
    }

    public static boolean containsOnlyDecimals(String value) {
        return StringUtils.containsOnly(value, DECIMALS);
    }

    public static boolean containsOnly(String value, String type) {
        switch (type) {
            case "a":
                return containsOnlyUpperCase(value);
            case "c":
                return containsOnlyAlphaNumeric(value);
            case "n":
                return containsOnlyNumeric(value);
            case "x":
                return containsOnlySwiftXSet(value);
            case "z":
                return containsOnlySwiftZSet(value);
            case "d":
                return containsOnlyDecimals(value);
            default:
                return false;
        }
    }

    public static int getNumber(String format) {
        List<Integer> numbers = getNumbers(format);
        return numbers.get(0);
    }

    public static List<Integer> getNumbers(String format) {
        List<Integer> numbers = new ArrayList<>();
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(format);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(0)));
        }
        return numbers;
    }

    public static String getType(String format) {
        String regex = "[acnxzd]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(format);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static List<String> getLines(String value) {
        return Arrays.asList(value.split("\r\n"));
    }
}
