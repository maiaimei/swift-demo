package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;

/**
 * validate fixed length or variable length of alphabetic, alpha-numeric, numeric, SWIFT X set, SWIFT Z set or decimals,
 * may be starts with slash,
 * e.g.
 * <p>16x, with a maximum of 16 characters</p>
 * <p>16!x, must be 16 characters</p>
 * <p>/16x, with a maximum of 16 characters and begin with a slash</p>
 * <p>/16!x, must be 16 characters and begin with a slash</p>
 * <p>x can be replaced by a|c|n|z|d</p>
 * <p>a: alphabetic</p>
 * <p>c: alpha-numeric</p>
 * <p>n: numeric</p>
 * <p>x: SWIFT X set</p>
 * <p>z: SWIFT Z set</p>
 * <p>d: decimals</p>
 */
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
