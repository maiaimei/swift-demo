package cn.maiaimei.example.validation;

import cn.maiaimei.example.validation.model.FieldConfig;
import cn.maiaimei.example.validation.model.MT798ValidationConfig;
import cn.maiaimei.example.validation.model.ValidationResult;
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
public class ValidationProcessor {
    @Autowired
    private Set<MT798ValidationConfig> ValidationConfigSet;

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
            result.addErrorMessage("20 Transaction Reference Number must not be blank");
        } else if (ValidatorUtils.gt(transactionReferenceNumber, 16) || ValidatorUtils.containsOther(transactionReferenceNumber, "x")) {
            result.addErrorMessage("20 Transaction Reference Number must match \"16x\"");
        }
    }

    private void validateSubMessageType(ValidationResult result, MT798 mt798) {
        String subMessageType = Optional.ofNullable(mt798.getField12()).orElseGet(Field12::new).getValue();
        if (StringUtils.isBlank(subMessageType)) {
            result.addErrorMessage("12 Sub-Message Type must not be blank");
        } else if (ValidatorUtils.ne(subMessageType, 3) || ValidatorUtils.containsOther(subMessageType, "n")) {
            result.addErrorMessage("12 Sub-Message Type must match \"3!n\"");
        }
    }

    private void validate77E(ValidationResult result, MT798 mt798) {
        String subMessageType = Optional.ofNullable(mt798.getField12()).orElseGet(Field12::new).getValue();
        if (StringUtils.isBlank(subMessageType)) {
            return;
        }
        Optional<MT798ValidationConfig> configOptional = ValidationConfigSet.stream().filter(w -> subMessageType.equals(w.getSubMessageType())).findAny();
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
        MT798ValidationConfig configBean = configOptional.get();
        List<FieldConfig> fieldConfigs = configBean.getFields();
        ValidatorUtils.validateFields(result, block, fieldConfigs);
    }

    private void validate27A(ValidationResult result, SwiftTagListBlock block) {
        Tag tag27A = block.getTagByName("27A");
        if (tag27A == null || StringUtils.isBlank(tag27A.getValue())) {
            result.addErrorMessage("27A Message Index/Total must not be blank");
        } else if (!ValidatorUtils.isMatchMessageIndexTotal(tag27A.getValue())) {
            result.addErrorMessage("27A Message Index/Total must match \"1!n/1!n\"");
        }
    }
}
