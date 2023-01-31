package cn.maiaimei.framework.swift.validation;

import cn.maiaimei.framework.swift.validation.validator.*;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ValidationEngine {
    private static final String FIELD_77E_NAME = "77E";

    private Set<FieldValidator> fieldValidatorComposite = new LinkedHashSet<>();
    @Autowired
    private Set<FieldValidator> fieldValidatorSet;
    @Autowired
    private GeneralFieldValidator generalFieldValidator;
    @Autowired
    private MultilineSwiftSetFieldValidator multilineSwiftSetFieldValidator;
    @Autowired
    private DateTimeFieldValidator dateTimeFieldValidator;
    @Autowired
    private BICFieldValidator bicFieldValidator;
    @Autowired
    private AmountFieldValidator amountFieldValidator;

    @PostConstruct
    public void init() {
        fieldValidatorComposite.add(generalFieldValidator);
        fieldValidatorComposite.add(multilineSwiftSetFieldValidator);
        fieldValidatorComposite.add(dateTimeFieldValidator);
        fieldValidatorComposite.add(bicFieldValidator);
        fieldValidatorComposite.add(amountFieldValidator);
    }

    public ValidationResult validate(MT798 mt798) {
        ValidationResult result = new ValidationResult();
        result.setHasError(false);
        result.setErrorMessages(new ArrayList<>());
        // validate Transaction Reference Number
        Field20 field20 = mt798.getField20();
        result.addErrorMessage(generalFieldValidator.validate(field20, "16x", field20.getValue()));
        Field12 field12 = mt798.getField12();
        // validate Sub-Message Type
        String subMessageType = Optional.ofNullable(field12).orElseGet(Field12::new).getValue();
        result.addErrorMessage(generalFieldValidator.validate(field12, "3!n", subMessageType));
        // validate 77E
        SwiftTagListBlock block = mt798.getSwiftMessage().getBlock4().getSubBlockAfterFirst(FIELD_77E_NAME, Boolean.FALSE);
        List<Tag> tags = block.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            result.addErrorMessage(ValidationError.mustNotBeBlank(FIELD_77E_NAME));
        }
        checkError(result);
        if (StringUtils.isBlank(subMessageType)) {
            return result;
        }
        // TODO: validate mandatory field
        // validate field by tag
        for (Tag tag : tags) {
            String name = tag.getName();
            String value = tag.getValue();
            String format = getFormat(block, name);
            FieldValidator<Field> fieldValidator = getFieldValidator(name, format);
            if (fieldValidator == null) {
                result.addErrorMessage("Can't find the fieldValidator for Field" + name + "");
                continue;
            }
            result.addErrorMessage(fieldValidator.validate(block.getFieldByName(name), format, value));
        }
        checkError(result);
        return result;
    }

    private String getFormat(SwiftTagListBlock block, String name) {
        Field field = block.getFieldByName(name);
        String validatorPattern = field.validatorPattern();
        if (ValidatorUtils.isMatchGeneralFormat(validatorPattern)
                || ValidatorUtils.isMatchMultilineSwiftSetFormat(validatorPattern)) {
            return validatorPattern;
        }
        switch (validatorPattern) {
            case "16x(***)": {
                return "16x";
            }
            case "35x[$35x]0-3":
                return "4*35x";
            default:
                return validatorPattern;
        }
    }

    private FieldValidator getFieldValidator(String name, String format) {
        for (FieldValidator fieldValidator : fieldValidatorSet) {
            if (fieldValidator.supports(name)) {
                return fieldValidator;
            }
        }
        if (StringUtils.isBlank(format)) {
            return null;
        }
        for (FieldValidator fieldValidator : fieldValidatorComposite) {
            if (fieldValidator.supports(format)) {
                return fieldValidator;
            }
        }
        return null;
    }

    private void checkError(ValidationResult result) {
        if (!CollectionUtils.isEmpty(result.getErrorMessages())) {
            result.setHasError(true);
        }
    }
}
