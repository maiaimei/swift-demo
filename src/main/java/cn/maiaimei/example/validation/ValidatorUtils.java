package cn.maiaimei.example.validation;

import com.prowidesoftware.swift.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    private static final String SLASH = "/";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String RIGHT_SQUARE_BRACKET = "]";
    private static final String EXCLAMATORY_MARK = "!";
    private static final String ASTERISK = "*";

    /**
     * variable length alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, e.g. 16x
     */
    public static final String VARIABLE_LENGTH_CHAR = "^\\[?/?[1-9]{1}[0-9]*[acnxzd]]?$";

    /**
     * fixed length alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals, e.g. 5!c
     */
    public static final String FIXED_LENGTH_CHAR = "^\\[?/?[1-9]{1}[0-9]*![acnxzd]]?$";

    /**
     * multiline SWIFT X set or SWIFT Z set, e.g. 4*35x
     */
    public static final String MULTILINE_SWIFT_SET = "^[1-9]{1}[0-9]*\\*[1-9]{1}[0-9]*[xz]$";

    /**
     * combination of format, e.g. 8!n4!n, 1!n/1!n, 6!n[/6!n], 3!a15d etc
     */
    public static final String COMPOSITE_FORMAT_A = "^[1-9]{1}[0-9]*![acnxzd](\\[?/?[1-9]{1}[0-9]*!?[acnxzd]]?)*$";

    /**
     * combination of format, e.g. 4!c/35x, 4!c[/35x] etc
     */
    public static final String COMPOSITE_FORMAT_B = "^[1-9]{1}[0-9]*![acnxzd]\\[?/[1-9]{1}[0-9]*[xz]]?$";

    private static final Pattern VARIABLE_LENGTH_CHAR_PATTERN = Pattern.compile(VARIABLE_LENGTH_CHAR);
    private static final Pattern FIXED_LENGTH_CHAR_PATTERN = Pattern.compile(FIXED_LENGTH_CHAR);
    private static final Pattern MULTILINE_SWIFT_SET_PATTERN = Pattern.compile(MULTILINE_SWIFT_SET);
    private static final Pattern COMPOSITE_FORMAT_A_PATTERN = Pattern.compile(COMPOSITE_FORMAT_A);
    private static final Pattern COMPOSITE_FORMAT_B_PATTERN = Pattern.compile(COMPOSITE_FORMAT_B);

    private static final Map<String, String> ERROR_MESSAGE_MAP = new HashMap<>();

    static {
        ERROR_MESSAGE_MAP.put("a", " contain alphabetic, capital letters (A through Z), upper case only");
        ERROR_MESSAGE_MAP.put("c", " contain alpha-numeric capital letters (upper case), and digits only");
        ERROR_MESSAGE_MAP.put("n", " contain numeric, digits (0 through 9) only");
        ERROR_MESSAGE_MAP.put("x", " contain SWIFT X set only");
        ERROR_MESSAGE_MAP.put("z", " contain SWIFT Z set only");
        ERROR_MESSAGE_MAP.put("d", " contain decimals, including decimal comma ',' preceding the fractional part. " +
                "The fractional part may be missing, but the decimal comma must always be present");
    }

    public static boolean eq(String value, int length) {
        return value.length() == length;
    }

    public static boolean ne(String value, int length) {
        return value.length() != length;
    }

    public static boolean le(String value, int maxlength) {
        return value.length() <= maxlength;
    }

    public static boolean gt(String value, int maxlength) {
        return value.length() > maxlength;
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

    public static boolean isFixedLength(String format) {
        return format.contains(EXCLAMATORY_MARK);
    }

    public static boolean isMultiline(String format) {
        return format.contains(ASTERISK);
    }

    public static boolean isOptional(String format) {
        return format.startsWith(LEFT_SQUARE_BRACKET) && format.endsWith(RIGHT_SQUARE_BRACKET);
    }

    // TODO: validateFields
    public static void validateFields(ValidationResult result, List<Tag> tags, List<ValidationConfigItem> validationConfigItems) {
        for (ValidationConfigItem validationConfigItem : validationConfigItems) {
            String tagName = validationConfigItem.getTag();
            Optional<Tag> tagOptional = tags.stream().filter(w -> tagName.equals(w.getName())).findAny();
            if (!tagOptional.isPresent()) {
                if (validationConfigItem.isRequired()) {
                    result.addErrorMessage(tagName + " must be present");
                }
                continue;
            }
            String tagValue = tagOptional.get().getValue();
            if (StringUtils.isBlank(tagValue) && validationConfigItem.isRequired()) {
                result.addErrorMessage(tagName + " must not be blank");
                continue;
            }
            int beginIndex = 0;
            String format = validationConfigItem.getFormat();
            List<String> formats = splitFormat(format);
            for (int i = 0; i < formats.size(); i++) {
                String fmt = formats.get(i);
                List<Integer> numbers = getNumbers(fmt);
                String type = getType(fmt);

                // validate field value length
                if (isMultiline(fmt)) {
                    int rowcount = numbers.get(0);
                    int maxlength = numbers.get(1);
                    List<String> lines = getLines(tagValue);
                    if (lines.size() > rowcount
                            || lines.stream().anyMatch(line -> gt(line, maxlength) || !containsOnly(line, type))) {
                        result.addErrorMessage(tagName + " up to " + rowcount + " lines, with a maximum of " + maxlength + " characters per line");
                    }
                } else {
                    if (isFixedLength(fmt)) {
                        int length = numbers.get(0);
                        if (ne(tagValue, length)) {
                            result.addErrorMessage(tagName + " length must be " + length);
                        }
                    } else {
                        int maxlength = numbers.get(0);
                        if (gt(tagValue, maxlength)) {
                            result.addErrorMessage(tagName + " length must be less than or equal to " + maxlength);
                        }
                    }
                }

                // validate field value characters
                if (!containsOnly(tagValue, type)) {
                    result.addErrorMessage(tagName + ERROR_MESSAGE_MAP.get(type));
                }
            }

        }
    }

    public static boolean isMatchVariableLengthChar(String format) {
        return VARIABLE_LENGTH_CHAR_PATTERN.matcher(format).matches();
    }

    public static boolean isMatchFixedLengthChar(String format) {
        return FIXED_LENGTH_CHAR_PATTERN.matcher(format).matches();
    }

    public static boolean isMatchMultilineSwiftSet(String format) {
        return MULTILINE_SWIFT_SET_PATTERN.matcher(format).matches();
    }

    /**
     * validate combination of format,
     * <p>see {@link ValidatorUtils#COMPOSITE_FORMAT_A}</p>
     * <p>see {@link ValidatorUtils#COMPOSITE_FORMAT_B}</p>
     */
    public static boolean isMatchCompositeFormat(String format) {
        return COMPOSITE_FORMAT_A_PATTERN.matcher(format).matches()
                || COMPOSITE_FORMAT_B_PATTERN.matcher(format).matches();
    }

    public static boolean validateVariableLengthChar(ValidationConfigItem configItem, String value) {
        return validateChar(configItem, value, ValidatorUtils::le);
    }

    public static boolean validateFixedLengthChar(ValidationConfigItem configItem, String value) {
        return validateChar(configItem, value, ValidatorUtils::eq);
    }

    private static boolean validateChar(ValidationConfigItem configItem, String value, BiPredicate<String, Integer> biPredicate) {
        String format = configItem.getFormat();
        if (StringUtils.isBlank(value)) {
            return isOptional(format);
        }
        boolean isValid;
        String valueToValidate;
        int length = getNumber(format);
        String type = getType(format);
        if (format.startsWith(SLASH)) {
            valueToValidate = value.substring(1);
            isValid = value.startsWith(SLASH);
        } else {
            valueToValidate = value;
            isValid = true;
        }
        isValid = isValid && biPredicate.test(valueToValidate, length) && containsOnly(valueToValidate, type);
        if (!isValid || CollectionUtils.isEmpty(configItem.getRules())) {
            return isValid;
        }
        return validateRules(valueToValidate, configItem.getRules());
    }

    public static boolean validateMultilineSwiftSet(ValidationConfigItem configItem, String value) {
        String format = configItem.getFormat();
        if (StringUtils.isBlank(value)) {
            return isOptional(format);
        }
        List<Integer> numbers = getNumbers(format);
        int rowcount = numbers.get(0);
        int maxlength = numbers.get(1);
        String type = getType(format);
        List<String> lines = getLines(value);
        if (lines.size() > rowcount) {
            return false;
        }
        return lines.stream().allMatch(line -> le(line, maxlength) && containsOnly(line, type));
    }

    public static boolean validateCompositeFormat(ValidationConfigItem configItem, String value) {
        List<Boolean> result = new ArrayList<>();
        List<String> formats = splitFormat(configItem.getFormat());
        String fmt;
        String val;
        int beginIndex = 0;
        for (int i = 0; i < formats.size(); i++) {
            fmt = formats.get(i);
            int finalI = i;
            List<ValidationConfigRule> rules = Optional.ofNullable(configItem.getRules()).orElseGet(ArrayList::new).stream().filter(w -> finalI == w.getKey()).collect(Collectors.toList());
            ValidationConfigItem cfgItem = ValidationConfigItem.builder()
                    .format(fmt)
                    .rules(rules)
                    .build();
            if (isMatchFixedLengthChar(fmt)) {
                int length = getNum(fmt);
                val = getVal(value, beginIndex, length);
                beginIndex += length;
                result.add(validateFixedLengthChar(cfgItem, val));
            } else if (isMatchVariableLengthChar(fmt)) {
                int maxlength = getNum(fmt);
                val = getVal(value, beginIndex, maxlength);
                beginIndex += maxlength;
                result.add(validateVariableLengthChar(cfgItem, val));
            }
        }
        return result.stream().allMatch(r -> r);
    }

    private static boolean validateRules(String value, List<ValidationConfigRule> rules) {
        List<Boolean> result = new ArrayList<>();
        for (ValidationConfigRule rule : rules) {
            if ("enum".equals(rule.getType())) {
                result.add(rule.getEnumItems().contains(value));
            } else if ("datetime".equals(rule.getType())) {
                result.add(validateDatetime(rule.getDatetimeFormat(), value));
            }
        }
        return result.stream().allMatch(r -> r);
    }

    private static boolean validateDatetime(String format, String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(value);
            return value.equals(dateFormat.format(date));
        } catch (ParseException e) {
            return false;
        }
    }

    private static String getVal(String value, int beginIndex, int offset) {
        return beginIndex + offset > value.length() ? value.substring(beginIndex) : value.substring(beginIndex, beginIndex + offset);
    }

    private static int getNum(String fmt) {
        int num = getNumber(fmt);
        if (fmt.startsWith(SLASH)) {
            num += 1;
        }
        return num;
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

    public static List<String> splitFormat(String format) {
        List<String> formats = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        String[] chars = format.split("");
        for (String c : chars) {
            tmp.add(c);
            if (tmp.contains("[") && tmp.contains("]")
                    || !tmp.contains("[") && "acnxzd".contains(c)) {
                formats.add(String.join("", tmp));
                tmp.clear();
            }
        }
        return formats;
    }
}
