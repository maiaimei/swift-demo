package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class GeneralFieldValidator<T extends Field> extends AbstractFieldValidator<T> {
    @Override
    public boolean supports(String nameOrFormat) {
        return ValidatorUtils.isMatchGeneralFormat(nameOrFormat);
    }

    @Override
    public String doValidate(T field, String format, String value) {
        return ValidatorUtils.validateGeneralFormat(field.getName(), format, value);
    }
}
