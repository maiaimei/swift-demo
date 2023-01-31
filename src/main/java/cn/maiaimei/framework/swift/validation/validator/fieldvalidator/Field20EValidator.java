package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.enumeration.Field20EReferenceEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field20E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Field20EValidator extends AbstractFieldValidator<Field20E> {
    private static final String NAME = "20E";

    @Override
    protected String doValidate(Field20E field, String format, String value) {
        String error = doValidateFixedCVarX(field, format, value, Boolean.TRUE);
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        if (!Field20EReferenceEnum.matches(field.getComponent1())) {
            return ValidationError.mustInEnum(NAME.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(0)),
                    Field20EReferenceEnum.getCodes());
        }
        return null;
    }

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }
}
