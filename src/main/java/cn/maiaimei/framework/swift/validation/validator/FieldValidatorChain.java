package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.model.mt.config.FieldComponentInfo;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.field.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FieldValidatorChain {

    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;

    @Autowired
    private FormatFieldValidatorComposite formatFieldValidatorComposite;

    @Autowired
    private PatternFieldValidatorComposite patternFieldValidatorComposite;

    @Autowired
    private TypeFieldValidatorComposite typeFieldValidatorComposite;

    @Autowired
    private EnumFieldValidator enumFieldValidator;

    @Autowired
    private ComponentValidator componentValidator;

    @PostConstruct
    public void setup() {
        mandatoryFieldValidator.setNextValidationHandler(formatFieldValidatorComposite);
        formatFieldValidatorComposite.setNextValidationHandler(patternFieldValidatorComposite);
        patternFieldValidatorComposite.setNextValidationHandler(typeFieldValidatorComposite);
        typeFieldValidatorComposite.setNextValidationHandler(enumFieldValidator);
        enumFieldValidator.setNextValidationHandler(componentValidator);
    }

    public void handleValidation(ValidationResult result, FieldComponentInfo fieldComponentInfo, Field field, String label, String value) {
        mandatoryFieldValidator.handleValidation(result, fieldComponentInfo, field, label, value);
    }

}
