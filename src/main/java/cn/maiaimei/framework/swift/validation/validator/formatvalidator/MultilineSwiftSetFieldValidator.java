package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.FormatFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.SwiftParseUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Validate multi line SWIFT X set or SWIFT Z set
 */
@Component
public class MultilineSwiftSetFieldValidator implements FormatFieldValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return ValidatorUtils.isMultilineSwiftSet(format);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        String format = fieldComponentInfo.getFormat();
        List<Integer> numbers = ValidatorUtils.getNumbers(format);
        int rowcount = numbers.get(0);
        int maxlength = numbers.get(1);
        String type = ValidatorUtils.getType(format);
        List<String> lines = SwiftParseUtils.getLines(value);
        boolean isInvalid = lines.size() > rowcount
                || lines.stream().anyMatch(line -> ValidatorUtils.gt(line, maxlength) || ValidatorUtils.containsOther(line, type));
        if (isInvalid) {
            return ValidationError.mustBeMultilineSwiftSet(label, rowcount, maxlength, type, value);
        }
        return null;
    }
}
