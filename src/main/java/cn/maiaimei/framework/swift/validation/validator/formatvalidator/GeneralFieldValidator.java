package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

@Component
public class GeneralFieldValidator<T extends Field> extends AbstractFormatValidator<T> {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchGeneralFormat(format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        return ValidatorUtils.validateGeneralFormat(tag, format, value);
    }
}
