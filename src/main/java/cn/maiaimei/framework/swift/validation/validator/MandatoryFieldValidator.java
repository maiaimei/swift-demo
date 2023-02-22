package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validate mandatory field
 */
@Component
public class MandatoryFieldValidator implements FieldValidator {
    @Override
    public String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        if (ValidatorUtils.isMandatory(fieldComponentInfo.getStatus()) && StringUtils.isBlank(value)) {
            return ValidationError.mustNotBeBlank(label);
        }
        return null;
    }
}
