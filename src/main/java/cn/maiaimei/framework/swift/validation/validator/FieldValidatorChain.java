package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class FieldValidatorChain {

    /**
     * add @Lazy annotation to solve circular references
     */
    @Lazy
    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;

    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        mandatoryFieldValidator.handleValidation(result, fieldComponentInfo, field, label, value);
    }

}
