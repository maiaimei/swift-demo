package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.FieldEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * validate field enum, e.g. CODES
 */
@Component
public class EnumFieldValidator<T extends Field> implements FieldValidator<T> {
    @Override
    public boolean supportsFormat(String format) {
        return false;
    }

    @Override
    public boolean supportsName(String name) {
        return false;
    }

    @Override
    public String validate(T field, String tag, String format, String value) {
        return null;
    }

    public String validate(T field, String tag, String value, FieldEnum fieldEnum) {
        String valueToValidate;
        int number = fieldEnum.getNumber();
        if (number != 0) {
            valueToValidate = field.getComponent(number);
        } else {
            valueToValidate = value;
        }
        String name = tag.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(number));
        if (fieldEnum.isRequired() && StringUtils.isBlank(valueToValidate)) {
            return ValidationError.mustNotBeBlank(name);
        }
        List<String> enumItems = fieldEnum.getEnumItems();
        if (!enumItems.contains(valueToValidate)) {
            return ValidationError.mustInEnum(name, valueToValidate, enumItems);
        }
        return null;
    }
}
