package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.model.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Validate multi line SWIFT X set or SWIFT Z set
 */
@Component
public class MultilineSwiftSetFieldValidator extends AbstractFormatValidator {
    @Override
    public boolean supportsFormat(Field field, String format) {
        return ValidatorUtils.isMatchMultilineSwiftSet(format);
    }

    @Override
    public String validate(FieldComponentInfo validationInfo, Field field, String label, String value) {
        String format = validationInfo.getFormat();
        List<Integer> numbers = ValidatorUtils.getNumbers(format);
        int rowcount = numbers.get(0);
        int maxlength = numbers.get(1);
        String type = getType(format);
        if (!ValidatorUtils.validateMultilineSwiftSet(rowcount, maxlength, type, value)) {
            return ValidationError.mustBeMultilineSwiftSet(label, rowcount, maxlength, type, value);
        }
        return null;
    }
}
