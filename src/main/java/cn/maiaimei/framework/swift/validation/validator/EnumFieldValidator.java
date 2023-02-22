package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Validate field by enum
 */
@Component
public class EnumFieldValidator implements FieldValidator {
    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        List<String> options = fieldComponentInfo.getOptions();
        if (!CollectionUtils.isEmpty(options) && !options.contains(value)) {
            return ValidationError.mustIn(label, value, options);
        }
        return null;
    }
}
