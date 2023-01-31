package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.SwiftParseUtils;
import org.springframework.stereotype.Component;

/**
 * validate multi line SWIFT X set or SWIFT Z set, e.g.
 * <p>4*35x, up to 4 lines, with a maximum of 35 characters per line, SWIFT X set only</p>
 * <p>50*65z, up to 50 lines, with a maximum of 65 characters per line, SWIFT Z set only</p>
 */
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
