package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field27A;
import org.springframework.stereotype.Component;

@Component
public class Field27AValidator extends AbstractFieldValidator<Field27A> {
    private static final String NAME = "27A";
    private static final String FORMAT = "1!n/1!n";

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }

    @Override
    public String doValidate(Field27A field, String format, String value) {
        if (!ValidatorUtils.isMatchMessageIndexTotal(value)) {
            return ValidationError.mustMatchFormat(NAME, FORMAT, value);
        }
        return null;
    }
}
