package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.enumeration.Field23EMethodOfTransmissionEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field23E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Field23EValidator extends AbstractFieldValidator<Field23E> {
    private static final String NAME = "23E";

    @Override
    protected String doValidate(Field23E field, String format, String value) {
        String error = doValidateFixedCVarX(field, format, value, Boolean.TRUE);
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        if (!Field23EMethodOfTransmissionEnum.matches(field.getComponent1())) {
            return ValidationError.mustInEnum(NAME.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(0)),
                    Field23EMethodOfTransmissionEnum.getCodes());
        }
        return null;
    }

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }
}
