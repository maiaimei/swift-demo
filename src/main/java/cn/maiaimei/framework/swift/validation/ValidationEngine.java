package cn.maiaimei.framework.swift.validation;

import cn.maiaimei.framework.swift.validation.mt.GeneralMTValidation;
import cn.maiaimei.framework.swift.validation.mt.MTValidation;
import cn.maiaimei.framework.swift.validation.validator.formatvalidator.GeneralFieldValidator;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Component
public class ValidationEngine {
    private static final String FIELD_77E_NAME = "77E";

    @Autowired
    private GeneralFieldValidator generalFieldValidator;

    @Autowired
    private Set<MTValidation> mtValidationSet;

    public ValidationResult validate(MT798 mt798) {
        ValidationResult result = new ValidationResult();
        result.setHasError(false);
        result.setErrorMessages(new ArrayList<>());
        // validate Transaction Reference Number
        Field20 field20 = mt798.getField20();
        result.addErrorMessage(generalFieldValidator.validate(field20, "20", "16x", field20.getValue()));
        Field12 field12 = mt798.getField12();
        // validate Sub-Message Type
        String subMessageType = Optional.ofNullable(field12).orElseGet(Field12::new).getValue();
        result.addErrorMessage(generalFieldValidator.validate(field12, "12", "3!n", subMessageType));
        // validate 77E
        SwiftTagListBlock block = mt798.getSwiftMessage().getBlock4().getSubBlockAfterFirst(FIELD_77E_NAME, Boolean.FALSE);
        if (CollectionUtils.isEmpty(block.getTags())) {
            result.addErrorMessage(ValidationError.mustNotBeBlank(FIELD_77E_NAME));
        }
        checkError(result);
        if (StringUtils.isBlank(subMessageType)) {
            return result;
        }
        MTValidation validation = getMTValidation(subMessageType);
        validation.validateFields(result, mt798, block);
        checkError(result);
        return result;
    }

    private MTValidation getMTValidation(String subMessageType) {
        return mtValidationSet.stream().filter(w -> w.supports(subMessageType)).findAny().orElseGet(GeneralMTValidation::new);
    }

    private void checkError(ValidationResult result) {
        if (!CollectionUtils.isEmpty(result.getErrorMessages())) {
            result.setHasError(true);
        }
    }
}
