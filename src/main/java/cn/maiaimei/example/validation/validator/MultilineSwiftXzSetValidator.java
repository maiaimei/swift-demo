package cn.maiaimei.example.validation.validator;

import cn.maiaimei.example.validation.ValidatorUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class MultilineSwiftXzSetValidator implements SwiftValidator {
    @Override
    public boolean supportValidate(String format) {
        return Pattern.matches(ValidatorUtils.MULTILINE_SWIFT_XZ_SET, format);
    }

    @Override
    public boolean doValidate(String format, String value) {
        List<Integer> numbers = ValidatorUtils.getNumbers(format);
        int rowcount = numbers.get(0);
        int maxlength = numbers.get(1);
        String type = ValidatorUtils.getType(format);
        List<String> lines = ValidatorUtils.getLines(value);
        if (lines.size() > rowcount) {
            return false;
        }
        return lines.stream().allMatch(line -> ValidatorUtils.le(line, maxlength) && ValidatorUtils.containsOnly(line, type));
    }
}
