package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.enumeration.Field25FTextPurposeEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field25F;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Field25FValidator extends AbstractFieldValidator<Field25F> {
    private static final String NAME = "25F";

    @Override
    protected String doValidate(Field25F field, String format, String value) {
        String name = NAME.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(0));
        String error = ValidatorUtils.validateGeneralFormat(name, format, value);
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        if (!Field25FTextPurposeEnum.matches(field.getComponent1())) {
            return ValidationError.mustInEnum(name, Field25FTextPurposeEnum.getCodes());
        }
        return null;
    }

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }
}
