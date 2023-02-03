package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import cn.maiaimei.framework.swift.validation.validator.FieldValidatorChain;
import cn.maiaimei.framework.swift.validation.validator.MultipleComponentsFieldValidator;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractValidationEngine {

    private static final String IN_SEQUENCE = "In Sequence %s, ";

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    @Autowired
    private MultipleComponentsFieldValidator multipleComponentsFieldValidator;

    protected void validateMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags) {
        validateSequenceMandatoryFields(result, fieldInfos, tags, StringUtils.EMPTY);
    }

    protected void validateTags(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, SwiftTagListBlock block) {
        validateSequenceTags(result, fieldInfos, tags, block, StringUtils.EMPTY);
    }

    protected void validateSequenceMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, String sequenceName) {
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());
        for (FieldInfo fieldInfo : fieldInfos) {
            if (!fieldInfo.isMandatory()) {
                continue;
            }
            String tag = fieldInfo.getTag();
            if (!tagNames.contains(tag)) {
                String label = getLabel(sequenceName, tag, fieldInfo.getLabel());
                result.addErrorMessage(ValidationError.mustBePresent(label));
            }
        }
    }

    protected void validateSequenceTags(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, SwiftTagListBlock block, String sequenceName) {
        for (Tag tag : tags) {
            String tagName = tag.getName();
            String tagValue = tag.getValue();
            Field field = block.getFieldByName(tagName);
            Optional<FieldInfo> fieldInfoOptional = fieldInfos.stream().filter(w -> w.getTag().equals(tagName)).findAny();
            if (!fieldInfoOptional.isPresent()) {
                continue;
            }
            FieldInfo fieldInfo = fieldInfoOptional.get();
            String label = getLabel(sequenceName, tagName, fieldInfo.getLabel());
            fieldValidatorChain.doValidation(result, fieldInfo, label, tagValue);
            multipleComponentsFieldValidator.validate(result, field, fieldInfo, label, tagValue);
        }
    }

    private String getLabel(String sequenceName, String tagName, String fieldLabel) {
        if (StringUtils.isBlank(sequenceName)) {
            return tagName.concat(StringUtils.SPACE).concat(fieldLabel);
        }
        return String.format(IN_SEQUENCE, sequenceName)
                .concat(tagName).concat(StringUtils.SPACE).concat(fieldLabel);
    }

}
