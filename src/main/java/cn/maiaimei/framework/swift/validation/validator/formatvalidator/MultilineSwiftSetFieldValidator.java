package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.SwiftParseUtils;
import org.springframework.stereotype.Component;

@Component
public class MultilineSwiftSetFieldValidator<T extends Field> extends AbstractFormatValidator<T> {
    @Override
    public boolean supportsFormat(String format) {
        return ValidatorUtils.isMatchMultilineSwiftSetFormat(format);
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        return ValidatorUtils.validateMultilineSwiftSet(tag, format, value, SwiftParseUtils.getLines(value));
    }
}
