package cn.maiaimei.framework.swift.validation;

import cn.maiaimei.framework.swift.validation.model.FieldComponentConfig;
import cn.maiaimei.framework.swift.validation.model.FieldConfig;
import cn.maiaimei.framework.swift.validation.model.ValidationResult;
import cn.maiaimei.framework.swift.validation.model.ValidationRule;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.SwiftParseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ValidatorUtils {
    private static final String EXCLAMATORY_MARK = "!";
    private static final String SLASH = "/";

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

    private static final String DECIMALS_FORMAT_REGEX = "^[0-9]+,$";

    private static final String BIC_FORMAT = "4!a2!a2!c[3!c]";
    private static final String BIC_FORMAT_REGEX = "^[A-Z]{6}[A-Za-z]{2}([A-Za-z]{3})?$";

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
     * Message Index/Total
     * <p>1!n/1!n</p>
     */
    private static final String MESSAGE_INDEX_TOTAL_FORMAT_REGEX = "^[1-9]{1}/[1-9]{1}$";

    /**
     * Message Creation DateTime
     * <p>8!n4!n</p>
     * <p>(Date)(Time)</p>
     */
    private static final String MESSAGE_CREATION_DATETIME_FORMAT = "8!n4!n";
    private static final String MESSAGE_CREATION_DATETIME_FORMAT_REGEX = "^[0-9]{12}$";

    /**
     * Reference Date Format
     * <p>6!n[/6!n]</p>
     * <p>(Date 1)(Date 2)</p>
     */
    private static final String REFERENCE_DATE_FORMAT = "6!n[/6!n]";
    private static final String REFERENCE_DATE_FORMAT_REGEX = "^[0-9]{6}(/[0-9]{6})?$";

    private static final Pattern DECIMALS_FORMAT_PATTERN = Pattern.compile(DECIMALS_FORMAT_REGEX);
    private static final Pattern GENERAL_FORMAT_PATTERN = Pattern.compile(GENERAL_FORMAT_REGEX);
    private static final Pattern MULTILINE_SWIFT_SET_FORMAT_PATTERN = Pattern.compile(MULTILINE_SWIFT_SET_FORMAT_REGEX);
    private static final Pattern BIC_FORMAT_PATTERN = Pattern.compile(BIC_FORMAT_REGEX);
    private static final Pattern MESSAGE_INDEX_TOTAL_FORMAT_PATTERN = Pattern.compile(MESSAGE_INDEX_TOTAL_FORMAT_REGEX);
    private static final Pattern MESSAGE_CREATION_DATETIME_FORMAT_PATTERN = Pattern.compile(MESSAGE_CREATION_DATETIME_FORMAT_REGEX);
    private static final Pattern REFERENCE_DATE_FORMAT_PATTERN = Pattern.compile(REFERENCE_DATE_FORMAT_REGEX);

    private static final String MUST_MATCH_FORMAT = "must_match_format";
    private static final Map<String, String> ERROR_MESSAGES = new HashMap<>();

    static {
        ERROR_MESSAGES.put(MUST_MATCH_FORMAT, "%s must match %s, invalid value is %s");
        ERROR_MESSAGES.put("a", "%s contain alphabetic, capital letters (A through Z), upper case only");
        ERROR_MESSAGES.put("c", "%s contain alpha-numeric capital letters (upper case), and digits only");
        ERROR_MESSAGES.put("n", "%s contain numeric, digits (0 through 9) only");
        ERROR_MESSAGES.put("x", "%s contain SWIFT X set only");
        ERROR_MESSAGES.put("z", "%s contain SWIFT Z set only");
        ERROR_MESSAGES.put("d", "%s contain decimals, including decimal comma ',' preceding the fractional part. " +
                "The fractional part may be missing, but the decimal comma must always be present");
    }

    public static boolean ne(String value, int length) {
        return value.length() != length;
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
        return StringUtils.containsOnly(value, DECIMALS) && DECIMALS_FORMAT_PATTERN.matcher(value).matches();
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

    public static void validateFields(ValidationResult result, SwiftTagListBlock block, List<FieldConfig> fieldConfigs) {
        for (FieldConfig fieldConfig : fieldConfigs) {
            String fieldTag = fieldConfig.getTag();
            Field field = block.getFieldByName(fieldTag);
            // validate mandatory field
            if (field == null) {
                if (fieldConfig.isRequired()) {
                    result.addErrorMessage(fieldTag + " must be present");
                }
                continue;
            }
            // original value: block.getTagValue(fieldTag), non-original value: field.getValue()
            String fieldValue = block.getTagValue(fieldTag);
            if (StringUtils.isBlank(fieldValue)) {
                if (fieldConfig.isRequired()) {
                    result.addErrorMessage(fieldTag + " must not be blank");
                }
                continue;
            }
            String fieldFormat = fieldConfig.getFormat();
            List<ValidationRule> rules = fieldConfig.getRules();
            validateFieldsByGeneralFormat(result, fieldTag, fieldFormat, fieldValue, rules);
            validateFieldsByMultilineSwiftSetFormat(result, fieldTag, fieldFormat, fieldValue);
            validateFieldsByComponents(result, field, fieldConfig, fieldValue);
            validateBIC(result, fieldTag, fieldFormat, fieldValue);
        }
    }

    private static void validateFieldsByComponents(ValidationResult result, Field field, FieldConfig fieldConfig, String fieldValue) {
        String fieldName = field.getName();
        String fieldFormat = fieldConfig.getFormat();
        if (isMatchGeneralFormat(fieldFormat)
                || isMatchMultilineSwiftSetFormat(fieldFormat)
                || isMatchBIC(fieldFormat)
                || !validateMessageCreationDateTime(result, fieldName, fieldFormat, fieldValue)
                || !validateReferenceDate(result, fieldName, fieldFormat, fieldValue)
            //|| CollectionUtils.isEmpty(fieldConfig.getComponents())
        ) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Field{} validatorPattern: {}, componentsSize: {}", fieldName, field.validatorPattern(), field.componentsSize());
        }
        List<String> componentLabels = field.getComponentLabels();
        for (int i = 0; i < field.componentsSize(); i++) {
            int componentIndex = i + 1;
            if (log.isDebugEnabled()) {
                log.debug("Field{} {} value is: {}", fieldName, componentLabels.get(i), field.getComponent(componentIndex));
            }
            Optional<FieldComponentConfig> componentConfigOptional = Optional.ofNullable(fieldConfig.getComponents()).orElseGet(ArrayList::new).stream().filter(w -> w.getIndex() == componentIndex).findAny();
            if (!componentConfigOptional.isPresent()) {
                continue;
            }
            FieldComponentConfig fieldComponentConfig = componentConfigOptional.get();
            String tagAndComponentLabel = fieldName + " " + componentLabels.get(i);
            String componentFormat = fieldComponentConfig.getFormat();
            String componentValue = field.getComponent(componentIndex);
            if (StringUtils.isBlank(componentValue)) {
                if (fieldComponentConfig.isRequired()) {
                    result.addErrorMessage(tagAndComponentLabel + " must not be blank");
                }
                continue;
            }
            validateFieldsByGeneralFormat(result, tagAndComponentLabel, componentFormat, componentValue, fieldComponentConfig.getRules());
            validateBIC(result, tagAndComponentLabel, componentFormat, componentValue);
        }
    }

    private static void validateFieldsByGeneralFormat(ValidationResult result, String tag, String format, String value, List<ValidationRule> rules) {
        if (StringUtils.isBlank(format) || !isMatchGeneralFormat(format)) {
            return;
        }
        if (isStartsWithSlash(format) && isStartsWithSlash(value)) {
            value = value.substring(1);
        }
        // validate field value length
        boolean isValidLength = true;
        boolean isFixedLength = isFixedLength(format);
        int length = getNumber(format);
        if (isFixedLength && ne(value, length)) {
            isValidLength = false;
            result.addErrorMessage(tag + " length must be " + length + ", invalid value is " + value);
        } else if (!isFixedLength && gt(value, length)) {
            isValidLength = false;
            result.addErrorMessage(tag + " length must be less than or equal to " + length + ", invalid value is " + value);
        }
        if (!isValidLength) {
            return;
        }
        // validate field value content
        String type = getType(format);
        if (containsOther(value, type)) {
            result.addErrorMessage(formatErrorMessage(type, tag) + ", invalid value is " + value);
            return;
        }
        // validate field value by custom rules
        validateRules(result, tag, value, rules);
    }

    private static void validateFieldsByMultilineSwiftSetFormat(ValidationResult result, String tag, String format, String value) {
        if (StringUtils.isBlank(format) || !isMatchMultilineSwiftSetFormat(format)) {
            return;
        }
        List<Integer> numbers = getNumbers(format);
        int rowcount = numbers.get(0);
        int maxlength = numbers.get(1);
        String type = getType(format);
        List<String> lines = SwiftParseUtils.getLines(value);
        if (lines.size() > rowcount
                || lines.stream().anyMatch(line -> gt(line, maxlength) || containsOther(line, type))) {
            result.addErrorMessage(tag + " up to " + rowcount + " lines, " +
                    "with a maximum of " + maxlength + " characters per line, " +
                    "contain SWIFT " + type.toUpperCase() + " set only, " +
                    "invalid value is " + value);
        }
    }

    private static void validateRules(ValidationResult result, String tag, String value, List<ValidationRule> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }
        for (ValidationRule rule : rules) {
            switch (rule.getType()) {
                case "enum": {
                    if (!validateEnum(value, rule.getEnumItems())) {
                        result.addErrorMessage(tag + " value must in \"" + String.join(",", rule.getEnumItems()) + "\", invalid value is " + value);
                    }
                    break;
                }
                case "datetime": {
                    if (!validateDatetime(value, rule.getDatetimePattern())) {
                        result.addErrorMessage(tag + " must match " + rule.getDatetimePattern() + ", invalid value is " + value);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private static boolean validateEnum(String value, List<String> enumItems) {
        return enumItems.contains(value);
    }

    private static boolean validateDatetime(String value, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean validateMessageCreationDateTime(ValidationResult result, String tag, String format, String value) {
        if (MESSAGE_CREATION_DATETIME_FORMAT.equals(format) && !MESSAGE_CREATION_DATETIME_FORMAT_PATTERN.matcher(value).matches()) {
            result.addErrorMessage(formatErrorMessage(MUST_MATCH_FORMAT, tag, format, value));
            return false;
        }
        return true;
    }

    private static boolean validateReferenceDate(ValidationResult result, String tag, String format, String value) {
        if (REFERENCE_DATE_FORMAT.equals(format) && !REFERENCE_DATE_FORMAT_PATTERN.matcher(value).matches()) {
            result.addErrorMessage(formatErrorMessage(MUST_MATCH_FORMAT, tag, format, value));
            return false;
        }
        return true;
    }

    private static void validateBIC(ValidationResult result, String tag, String format, String value) {
        if (isMatchBIC(format) && !BIC_FORMAT_PATTERN.matcher(value).matches()) {
            result.addErrorMessage(formatErrorMessage(MUST_MATCH_FORMAT, tag, format, value));
        }
    }

    private static boolean isFixedLength(String format) {
        return format.contains(EXCLAMATORY_MARK);
    }

    private static boolean isStartsWithSlash(String format) {
        return format.startsWith(SLASH);
    }

    private static boolean isMatchBIC(String format) {
        return BIC_FORMAT.equals(format);
    }

    public static boolean isMatchMessageIndexTotal(String value) {
        return MESSAGE_INDEX_TOTAL_FORMAT_PATTERN.matcher(value).matches();
    }

    private static boolean isMatchGeneralFormat(String format) {
        return GENERAL_FORMAT_PATTERN.matcher(format).matches();
    }

    private static boolean isMatchMultilineSwiftSetFormat(String format) {
        return MULTILINE_SWIFT_SET_FORMAT_PATTERN.matcher(format).matches();
    }

    private static List<Integer> getNumbers(String format) {
        List<Integer> numbers = new ArrayList<>();
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(format);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(0)));
        }
        return numbers;
    }

    private static int getNumber(String format) {
        List<Integer> numbers = getNumbers(format);
        return numbers.get(0);
    }

    private static String getType(String format) {
        String regex = "[acnxzd]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(format);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    private static String formatErrorMessage(String key, Object... args) {
        return String.format(ERROR_MESSAGES.get(key), args);
    }
}
