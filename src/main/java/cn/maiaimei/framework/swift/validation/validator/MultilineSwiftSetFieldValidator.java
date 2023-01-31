package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.SwiftParseUtils;
import org.springframework.stereotype.Component;

@Component
public class MultilineSwiftSetFieldValidator<T extends Field> extends AbstractFieldValidator<T> {
    @Override
    public boolean supports(String nameOrFormat) {
        return ValidatorUtils.isMatchMultilineSwiftSetFormat(nameOrFormat);
    }

    @Override
    public String doValidate(T field, String format, String value) {
        return ValidatorUtils.validateMultilineSwiftSet(field.getName(), format, value, SwiftParseUtils.getLines(value));
    }
}
