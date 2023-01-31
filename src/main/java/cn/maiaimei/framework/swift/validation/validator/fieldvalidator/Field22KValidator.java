package cn.maiaimei.framework.swift.validation.validator.fieldvalidator;

import cn.maiaimei.framework.swift.enumeration.Field22KTypeOfUndertakingEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.validator.AbstractFieldValidator;
import com.prowidesoftware.swift.model.field.Field22K;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Field22KValidator extends AbstractFieldValidator<Field22K> {
    private static final String NAME = "22K";

    @Override
    protected String doValidate(Field22K field, String format, String value) {
        String error = doValidateFixedCVarX(field, format, value, Boolean.TRUE);
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        if (!Field22KTypeOfUndertakingEnum.matches(field.getComponent1())) {
            return ValidationError.mustInEnum(NAME.concat(StringUtils.SPACE).concat(field.getComponentLabels().get(0)),
                    Field22KTypeOfUndertakingEnum.getCodes());
        }
        return null;
    }

    @Override
    public boolean supports(String nameOrFormat) {
        return NAME.equals(nameOrFormat);
    }
}
