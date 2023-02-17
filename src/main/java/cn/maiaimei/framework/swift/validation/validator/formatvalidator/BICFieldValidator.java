package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.validator.FormatFieldValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class BICFieldValidator implements FormatFieldValidator {

    private static final String FORMAT = "4!a2!a2!c[3!c]";
    private static final String REGEX = "^[A-Z]{6}[A-Za-z0-9]{2}([A-Za-z0-9]{3})?$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Override
    public boolean supportsFormat(Field field, String format) {
        return FORMAT.equals(format);
    }

    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        if (!PATTERN.matcher(value).matches()) {
            return ValidationError.mustMatchFormat(label, FORMAT, value);
        }
        return null;
    }
}
