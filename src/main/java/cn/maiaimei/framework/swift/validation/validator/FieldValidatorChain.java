package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldValidatorChain {
    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;

    public void handleValidation(ValidationResult result, FieldInfo fieldInfo, Field field, String label, String value) {
        mandatoryFieldValidator.handleValidation(result, fieldInfo, field, label, value);
    }
}
