package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.enumeration.Field12HWordingOfUndertakingEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field12H;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Field12HValidator extends AbstractFieldValidator<Field12H> {
    private static final String NAME = "12H";

    @Override
    protected String doValidate(Field12H field, String format, String value) {
        String error = doValidateFixedCVarX(field, format, value, Boolean.TRUE);
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        if (!Field12HWordingOfUndertakingEnum.matches(field.getComponent1())) {
            return ValidationError.mustInEnum(NAME.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(0)),
                    Field12HWordingOfUndertakingEnum.getCodes());
        }
        return null;
    }

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }
}
