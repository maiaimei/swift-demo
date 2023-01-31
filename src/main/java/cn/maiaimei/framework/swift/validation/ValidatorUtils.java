package cn.maiaimei.framework.swift.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class ValidatorUtils {
    private static final String EXCLAMATORY_MARK = "!";
    private static final String SLASH = "/";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "]";

    /**
     * a, alphabetic, capital letters (A through Z), upper case only
     */
    private static final char[] ALPHABETIC = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * c, alpha-numeric capital letters (upper case), and digits only
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
     * n, numeric, digits (0 through 9) only
     */
    private static final char[] NUMERIC = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * x, SWIFT X set:
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
     * z, SWIFT Z set:
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
     * d, decimals, including decimal comma ',' preceding
     * the fractional part. The fractional part may be
     * missing, but the decimal comma must always be
     * present
     */
    private static final char[] DECIMALS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ','
    };

    private static final String FORMAT_REGEX = "[acnxzd]";

    /**
     * fixed length or variable length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals,
     * may be starts with slash,
     * e.g.
     * <p>16x, with a maximum of 16 characters</p>
     * <p>16!x, must be 16 characters</p>
     * <p>/16x, with a maximum of 16 characters and begin with a slash</p>
     * <p>/16!x, must be 16 characters and begin with a slash</p>
     * <p>x can be replaced by a|c|n|z|d</p>
     * <p>a: alphabetic</p>
     * <p>c: alpha-numeric</p>
     * <p>n: numeric</p>
     * <p>x: SWIFT X set</p>
     * <p>z: SWIFT Z set</p>
     * <p>d: decimals</p>
     */
    private static final String GENERAL_FORMAT_REGEX = "^/?[1-9]{1}[0-9]*!?[acnxzd]$";

    /**
     * multi line SWIFT X set or SWIFT Z set, e.g.
     * <p>4*35x, up to 4 lines, with a maximum of 35 characters per line, SWIFT X set only</p>
     * <p>50*65z, up to 50 lines, with a maximum of 65 characters per line, SWIFT Z set only</p>
     */
    private static final String MULTILINE_SWIFT_SET_FORMAT_REGEX = "^[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*[xz]$";

    /**
     * optional Swift X set, Swift X set start with slash, e.g. [/35x]
     */
    private static final String VAR_X_FORMAT_REGEX = "\\[/[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*x]";

    /**
     * fixed length alpha-numeric and optional Swift X set, Swift X set start with slash, e.g. 4!c[/35x]
     */
    private static final String FIXED_C_VAR_X_FORMAT_REGEX = "^[1-9]{1}[0-9]*!c\\[/[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*x]$";

    private static final String DECIMALS_REGEX = "^[0-9]+,([0-9]{2})?$";

    private static final String NUMBER_REGEX = "\\d+";

    private static final String BIC_REGEX = "^[A-Z]{6}[A-Za-z0-9]{2}([A-Za-z0-9]{3})?$";

    /**
     * Index/Total
     * <p>1!n/1!n</p>
     */
    private static final String INDEX_TOTAL_REGEX = "^[1-9]{1}/[1-9]{1}$";

    /**
     * <DATE4><HHMM>
     * <p>8!n4!n</p>
     * <p>(Date)(Time)</p>
     */
    private static final String DATE4_HHMM_REGEX = "^[0-9]{12}$";

    /**
     * <DATE2>[/<DATE2>]
     * <p>6!n[/6!n]</p>
     * <p>(Date 1)(Date 2)</p>
     */
    private static final String DATE2_OPTIONAL_DATE2_REGEX = "^[0-9]{6}(/[0-9]{6})?$";

    /**
     * TODO: confirm fractional part
     * 3!a15d
     * (Currency)(Amount)
     */
    private static final String CUR_AMOUNT_15_REGEX = "^[A-Z]{3}[0-9]+,([0-9]{2})?$";

    private static final Pattern FORMAT_PATTERN = Pattern.compile(FORMAT_REGEX);
    private static final Pattern GENERAL_FORMAT_PATTERN = Pattern.compile(GENERAL_FORMAT_REGEX);
    private static final Pattern MULTILINE_SWIFT_SET_FORMAT_PATTERN = Pattern.compile(MULTILINE_SWIFT_SET_FORMAT_REGEX);
    private static final Pattern FIXED_C_VAR_X_FORMAT_PATTERN = Pattern.compile(FIXED_C_VAR_X_FORMAT_REGEX);
    private static final Pattern DECIMALS_PATTERN = Pattern.compile(DECIMALS_REGEX);
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    private static final Pattern BIC_PATTERN = Pattern.compile(BIC_REGEX);
    private static final Pattern INDEX_TOTAL_PATTERN = Pattern.compile(INDEX_TOTAL_REGEX);
    private static final Pattern DATE4_HHMM_PATTERN = Pattern.compile(DATE4_HHMM_REGEX);
    private static final Pattern DATE2_OPTIONAL_DATE2_PATTERN = Pattern.compile(DATE2_OPTIONAL_DATE2_REGEX);
    private static final Pattern CUR_AMOUNT_15_PATTERN = Pattern.compile(CUR_AMOUNT_15_REGEX);
    public static final Pattern VAR_X_FORMAT_PATTERN = Pattern.compile(VAR_X_FORMAT_REGEX);

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
        return StringUtils.containsOnly(value, DECIMALS) && DECIMALS_PATTERN.matcher(value).matches();
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

    public static boolean containsOther(String value, String type) {
        return !containsOnly(value, type);
    }

    public static String validateGeneralFormat(String name, String format, String value) {
        if (ValidatorUtils.isStartsWithSlash(format) && ValidatorUtils.isStartsWithSlash(value)) {
            value = value.substring(1);
        }
        int length = ValidatorUtils.getNumber(format);
        String type = ValidatorUtils.getType(format);
        boolean isFixedLength = ValidatorUtils.isFixedLength(format);
        if (isFixedLength && ValidatorUtils.ne(value, length)) {
            return ValidationError.mustBeFixedLength(type, name, length, value);
        }
        if (!isFixedLength && ValidatorUtils.gt(value, length)) {
            return ValidationError.mustBeVarLength(type, name, length, value);
        }
        return null;
    }

    public static String validateMultilineSwiftSet(String name, String format, String value, List<String> lines) {
        List<Integer> numbers = ValidatorUtils.getNumbers(format);
        int rowcount = numbers.get(0);
        int maxlength = numbers.get(1);
        String type = ValidatorUtils.getType(format);
        if (lines.size() > rowcount
                || lines.stream().anyMatch(line -> ValidatorUtils.gt(line, maxlength) || ValidatorUtils.containsOther(line, type))) {
            return ValidationError.mustBeMultilineSwiftSet(type, name, rowcount, maxlength, value);
        }
        return null;
    }

    public static String validateFixedCVarX(String name, String format, List<String> values, List<String> labels, boolean isOptionalVarX) {
        List<Integer> numbers = getNumbers(format);
        int length = numbers.get(0);
        int maxlength = numbers.size() > 1 ? numbers.get(1) : -1;
        String type = "x";
        String value = values.get(0);
        String value1 = values.get(1);
        String value2 = values.get(2);
        String label1 = labels.get(0);
        String label2 = labels.get(1);
        if (ne(value1, length)) {
            return ValidationError.mustBeFixedLength(type, name.concat(StringUtils.SPACE).concat(label1), length, value1);
        }
        if (!isOptionalVarX && StringUtils.isBlank(value2)) {
            return ValidationError.mustNotBeBlank(name.concat(StringUtils.SPACE).concat(label2));
        }
        if (StringUtils.isNotBlank(value2)) {
            if (!SLASH.equals(value.substring(length, length + 1))) {
                return ValidationError.mustMatchFormat(name, format, value);
            }
            if (gt(value2, maxlength)) {
                return ValidationError.mustBeVarLength(type, name.concat(StringUtils.SPACE).concat(label2), maxlength, value2);
            }
        }
        return null;
    }

    public static boolean validateDatetime(String value, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isMatchDate2OptionalDate2(String value) {
        return DATE2_OPTIONAL_DATE2_PATTERN.matcher(value).matches();
    }

    public static boolean isMatchDate4Hhmm(String value) {
        return DATE4_HHMM_PATTERN.matcher(value).matches();
    }

    public static boolean isMatchGeneralFormat(String format) {
        return GENERAL_FORMAT_PATTERN.matcher(format).matches();
    }

    public static boolean isMatchMultilineSwiftSetFormat(String format) {
        return MULTILINE_SWIFT_SET_FORMAT_PATTERN.matcher(format).matches();
    }

    public static boolean isMatchFixedCVarXFormat(String format) {
        return FIXED_C_VAR_X_FORMAT_PATTERN.matcher(format).matches();
    }

    public static boolean isMatchIndexTotal(String value) {
        return INDEX_TOTAL_PATTERN.matcher(value).matches();
    }

    public static boolean isMatchAmount12(String value) {
        return le(value, 12) && containsOnlyDecimals(value);
    }

    public static boolean isMatchCurrencyAmount15(String value) {
        return CUR_AMOUNT_15_PATTERN.matcher(value).matches();
    }

    public static boolean isMatchBIC(String value) {
        return BIC_PATTERN.matcher(value).matches();
    }

    public static boolean isFixedLength(String format) {
        return format.contains(EXCLAMATORY_MARK);
    }

    public static boolean isOptional(String format) {
        return format.startsWith(LEFT_SQUARE_BRACKET) && format.endsWith(RIGHT_SQUARE_BRACKET);
    }

    public static boolean isOptionalFormat(Pattern pattern, String format, int group) {
        Matcher matcher = pattern.matcher(format);
        int i = 0;
        while (matcher.find()) {
            if (i == group) {
                return isOptional(matcher.group(group));
            }
            i++;
        }
        return false;
    }

    public static boolean isStartsWithSlash(String format) {
        return format.startsWith(SLASH);
    }

    public static boolean isSupportsFormat(Map<String, Predicate<String>> predicateMap, String format) {
        for (Map.Entry<String, Predicate<String>> entry : predicateMap.entrySet()) {
            if (entry.getKey().equals(format)) {
                return true;
            }
        }
        return false;
    }

    public static List<Integer> getNumbers(String format) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = NUMBER_PATTERN.matcher(format);
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
        Matcher matcher = FORMAT_PATTERN.matcher(format);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    private ValidatorUtils() {
        throw new UnsupportedOperationException();
    }
}
