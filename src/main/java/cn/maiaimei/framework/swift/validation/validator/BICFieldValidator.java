package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class BICFieldValidator<T extends Field> extends AbstractFieldValidator<T> {
    /**
     * 4!a2!a2!c[3!c]
     * (Identifier Code)
     */
    private static final String FORMAT = "<BIC>";

    @Override
    public boolean supports(String nameOrFormat) {
        return FORMAT.equals(nameOrFormat);
    }

    @Override
    public String doValidate(T field, String format, String value) {
        if (ValidatorUtils.isMatchBIC(value)) {
            return ValidationError.mustMatchFormat(field.getName(), "4!a2!a2!c[3!c]", value);
        }
        return null;
    }
}
