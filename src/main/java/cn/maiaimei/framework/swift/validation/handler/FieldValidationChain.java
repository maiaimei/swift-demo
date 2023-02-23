package cn.maiaimei.framework.swift.validation.handler;

import cn.maiaimei.framework.swift.model.mt.config.FieldInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldValidationChain {
    @Autowired
    private MandatoryValidationHandler mandatoryFieldValidationHandler;

    public void handleValidation(ValidationResult result, FieldInfo fieldInfo, Field field, String label, String value) {
        mandatoryFieldValidationHandler.handleValidation(result, fieldInfo, field, label, value);
    }
}
