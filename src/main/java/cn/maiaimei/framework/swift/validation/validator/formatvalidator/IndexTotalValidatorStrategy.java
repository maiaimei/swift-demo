package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class IndexTotalValidatorStrategy<T extends Field> implements FormatValidatorStrategy<T> {
    private static final String FORMAT = "1!n/1!n";

    @Override
    public boolean supportsFormat(String format) {
        return FORMAT.equals(format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        if (!ValidatorUtils.isMatchIndexTotal(value)) {
            return ValidationError.mustMatchFormat(tag, FORMAT, value);
        }
        return null;
    }
}
