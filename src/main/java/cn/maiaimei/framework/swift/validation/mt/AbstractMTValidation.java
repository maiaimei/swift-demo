package cn.maiaimei.framework.swift.validation.mt;

import cn.maiaimei.framework.swift.validation.FieldEnum;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.validator.*;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

public abstract class AbstractMTValidation implements MTValidation {
    @Autowired
    private Set<AbstractFormatValidator> formatValidatorSet;
    @Autowired(required = false)
    private Set<AbstractTagNameValidator> tagNameValidatorSet;
    @Autowired
    private MandatoryFieldValidator mandatoryFieldValidator;
    @Autowired
    private EnumFieldValidator enumFieldValidator;

    @Override
    public void validateFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block) {
        validateMandatoryFields(result, mt798, block);
        validateFieldsByTags(result, mt798, block);
        doValidateFields(result, mt798, block);
    }

    protected void validateMandatoryFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block) {
        List<String> mandatoryFields = getMandatoryFields();
        if (!CollectionUtils.isEmpty(mandatoryFields)) {
            List<Tag> tags = block.getTags();
            for (String fieldName : mandatoryFields) {
                if (tags.stream().noneMatch(w -> fieldName.equals(w.getName()))) {
                    result.addErrorMessage(ValidationError.mustBePresent(fieldName));
                }
            }
            for (Tag tag : tags) {
                if (mandatoryFields.contains(tag.getName()) && StringUtils.isBlank(tag.getValue())) {
                    result.addErrorMessage(ValidationError.mustNotBeBlank(tag.getName()));
                }
            }
        }
    }

    protected void validateFieldsByTags(ValidationResult result, MT798 mt798, SwiftTagListBlock block) {
        List<FieldEnum> fieldEnums = getEnums();
        for (Tag tag : block.getTags()) {
            String name = tag.getName();
            String value = tag.getValue();
            String format = getFormat(block, name);
            Field field = block.getFieldByName(name);
            // validate field by format
            FieldValidator<Field> formatValidator = getFormatValidator(format);
            if (formatValidator != null) {
                result.addErrorMessage(formatValidator.validate(field, name, format, value));
            }
            // validate field by enum
            FieldEnum fieldEnum = fieldEnums.stream().filter(w -> w.getTag().equals(name)).findAny().orElseGet(FieldEnum::new);
            if (!CollectionUtils.isEmpty(fieldEnum.getEnumItems())) {
                result.addErrorMessage(enumFieldValidator.validate(field, name, value, fieldEnum));
            }
            // validate field by tag
            FieldValidator<Field> tagNameValidator = getTagNameValidator(format);
            if (tagNameValidator != null) {
                result.addErrorMessage(tagNameValidator.validate(field, name, format, value));
            }
        }
    }

    protected abstract void doValidateFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block);

    private String getFormat(SwiftTagListBlock block, String name) {
        Field field = block.getFieldByName(name);
        String validatorPattern = field.validatorPattern();
        if (ValidatorUtils.isMatchGeneralFormat(validatorPattern)
                || ValidatorUtils.isMatchMultilineSwiftSetFormat(validatorPattern)) {
            return validatorPattern;
        }
        // TODO: getFormat
        switch (validatorPattern) {
            case "1n/1n": {
                return "1!n/1!n";
            }
            case "16x(***)": {
                return "16x";
            }
            case "35x[$35x]0-3":
                return "4*35x";
            default:
                return validatorPattern;
        }
    }

    private FieldValidator getFormatValidator(String format) {
        if (!StringUtils.isBlank(format)) {
            for (FieldValidator fieldValidator : formatValidatorSet) {
                if (fieldValidator.supportsFormat(format)) {
                    return fieldValidator;
                }
            }
        }
        return null;
    }

    private FieldValidator getTagNameValidator(String name) {
        if (!CollectionUtils.isEmpty(tagNameValidatorSet)) {
            for (FieldValidator fieldValidator : tagNameValidatorSet) {
                if (fieldValidator.supportsName(name)) {
                    return fieldValidator;
                }
            }
        }
        return null;
    }
}
