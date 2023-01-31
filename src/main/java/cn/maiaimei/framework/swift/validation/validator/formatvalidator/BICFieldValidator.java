package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class BICFieldValidator<T extends Field> extends AbstractFormatValidator<T> {
    /**
     * 4!a2!a2!c[3!c]
     * (Identifier Code)
     */
    private static final String FORMAT = "<BIC>";

    @Override
    public boolean supportsFormat(String format) {
        return FORMAT.equals(format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        if (ValidatorUtils.isMatchBIC(value)) {
            return ValidationError.mustMatchFormat(tag, "4!a2!a2!c[3!c]", value);
        }
        return null;
    }
}
