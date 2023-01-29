package cn.maiaimei.example.validation;

import cn.maiaimei.example.validation.validator.FieldValidator;
import cn.maiaimei.example.validation.validator.GenericFieldValidator;
import com.google.gson.Gson;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ValidationUtils {
    private static final Gson GSON = new Gson();

    @Autowired
    private GenericFieldValidator genericFieldValidator;

    @Autowired
    private Set<FieldValidator> fieldValidatorSet;

    @Autowired
    private Set<ValidationConfigBean> ValidationConfigSet;

    public ValidationResult validate(MT798 mt798) {
        ValidationResult result = new ValidationResult();
        result.setHasError(false);
        result.setErrorMessages(new ArrayList<>());
        validateTransactionReferenceNumber(result, mt798);
        validateSubMessageType(result, mt798);
        validate77E(result, mt798);
        if (!CollectionUtils.isEmpty(result.getErrorMessages())) {
            result.setHasError(true);
        }
        return result;
    }

    private void validateTransactionReferenceNumber(ValidationResult result, MT798 mt798) {
        String transactionReferenceNumber = Optional.ofNullable(mt798.getField20()).orElseGet(Field20::new).getValue();
        if (StringUtils.isBlank(transactionReferenceNumber)) {
            result.addErrorMessage("20 (Transaction Reference Number) must not be blank");
        } else if (!ValidatorUtils.validateVariableLengthChar(ValidationConfigItem.builder().format("16x").build(), transactionReferenceNumber)) {
            result.addErrorMessage("20 (Transaction Reference Number) must match \"16x\"");
        }
    }

    private void validateSubMessageType(ValidationResult result, MT798 mt798) {
        String subMessageType = Optional.ofNullable(mt798.getField12()).orElseGet(Field12::new).getValue();
        if (StringUtils.isBlank(subMessageType)) {
            result.addErrorMessage("12 (Sub-Message Type) must not be blank");
        } else if (!ValidatorUtils.validateFixedLengthChar(ValidationConfigItem.builder().format("3!n").build(), subMessageType)) {
            result.addErrorMessage("12 (Sub-Message Type) must match \"3!n\"");
        }
    }

    private void validate77E(ValidationResult result, MT798 mt798) {
        String subMessageType = Optional.ofNullable(mt798.getField12()).orElseGet(Field12::new).getValue();
        if (StringUtils.isBlank(subMessageType)) {
            return;
        }
        Optional<ValidationConfigBean> configOptional = ValidationConfigSet.stream().filter(w -> subMessageType.equals(w.getSubMessageType())).findAny();
        if (!configOptional.isPresent()) {
            result.addErrorMessage("Can't find the validation config for Sub-Message Type \"" + subMessageType + "\"");
            return;
        }
        SwiftTagListBlock block = mt798.getSwiftMessage().getBlock4().getSubBlockAfterFirst("77E", false);
        List<Tag> tags = block.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            result.addErrorMessage("77E must not be empty");
            return;
        }
        validate27A(result, block);
        ValidationConfigBean configBean = configOptional.get();
        List<ValidationConfigItem> configItems = configBean.getConfigItems();
        validateFields(result, tags, configItems);
    }

    private void validate27A(ValidationResult result, SwiftTagListBlock block) {
        Tag tag27A = block.getTagByName("27A");
        if (tag27A == null || StringUtils.isBlank(tag27A.getValue())) {
            result.addErrorMessage("27A (Message Index/Total) must not be blank");
        } else if (!ValidatorUtils.validateCompositeFormat(ValidationConfigItem.builder().format("1!n/1!n").build(), tag27A.getValue())) {
            result.addErrorMessage("27A (Message Index/Total) must match \"1!n/1!n\"");
        }
    }

    private void validateFields(ValidationResult result, List<Tag> tags, List<ValidationConfigItem> configItems) {
        for (ValidationConfigItem configItem : configItems) {
            String tagName = configItem.getTag();
            String tagValue = null;
            Optional<Tag> tagOptional = tags.stream().filter(w -> tagName.equals(w.getName())).findAny();
            if (tagOptional.isPresent()) {
                tagValue = tagOptional.get().getValue();
            }
            if (StringUtils.isBlank(tagValue)) {
                if (configItem.isRequired()) {
                    result.addErrorMessage(tagName + " is mandatory");
                }
                continue;
            }
            FieldValidator validatorToUse = getValidator(configItem);
            boolean isValid = validatorToUse.validate(configItem, tagValue);
            if (!isValid) {
                result.addErrorMessage(tagName + " validate error, " +
                        "value: " + tagValue + ", " +
                        "config: " + GSON.toJson(configItem));
            }
        }
    }

    private FieldValidator getValidator(ValidationConfigItem configItem) {
        for (FieldValidator validator : fieldValidatorSet) {
            if (validator.supports(configItem)) {
                return validator;
            }
        }
        return genericFieldValidator;
    }
}
