package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.enumeration.Field29SCustomerIdentifierEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field29S;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Field29SValidator extends AbstractFieldValidator<Field29S> {
    private static final String NAME = "29S";

    @Override
    protected String doValidate(Field29S field, String format, String value) {
        String error = doValidateFixedCVarX(field, format, value, Boolean.TRUE);
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        if (!Field29SCustomerIdentifierEnum.matches(field.getComponent1())) {
            return ValidationError.mustInEnum(NAME.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(0)),
                    Field29SCustomerIdentifierEnum.getCodes());
        }
        return null;
    }

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }
}
