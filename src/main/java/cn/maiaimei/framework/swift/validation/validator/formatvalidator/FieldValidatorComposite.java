package cn.maiaimei.framework.swift.validation.validator.formatvalidator;

import cn.maiaimei.framework.swift.validation.validator.AbstractFormatValidator;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * validate other format
 * in order to validate special format, you need implements {@link FormatValidatorStrategy} interface
 */
@Component
public class FieldValidatorComposite<T extends Field> extends AbstractFormatValidator<T> {
    @Autowired
    private Set<FormatValidatorStrategy> formatValidatorStrategySet;

    @Override
    public boolean supportsFormat(String format) {
        return getFormatValidatorStrategy(format) != null;
    }

    @Override
    public boolean supportsName(String name) {
        return false;
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        FormatValidatorStrategy strategy = getFormatValidatorStrategy(format);
        return strategy.validate(field, tag, format, value);
    }

    private FormatValidatorStrategy getFormatValidatorStrategy(String format) {
        for (FormatValidatorStrategy strategy : formatValidatorStrategySet) {
            if (strategy.supportsFormat(format)) {
                return strategy;
            }
        }
        return null;
    }
}
