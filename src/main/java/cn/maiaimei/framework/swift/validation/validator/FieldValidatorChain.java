package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FieldValidatorChain {

    @Autowired
    private ApplicationContext applicationContext;

    private MandatoryFieldValidator mandatoryFieldValidator;

    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        if (mandatoryFieldValidator == null) {
            mandatoryFieldValidator = applicationContext.getBean(MandatoryFieldValidator.class);
        }
        mandatoryFieldValidator.handleValidation(result, fieldComponentInfo, field, label, value);
    }

}
