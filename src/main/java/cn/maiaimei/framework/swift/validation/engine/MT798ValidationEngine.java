package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.MessageValidationConfig;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MT798ValidationEngine {

    @Autowired
    @Qualifier("mt798ValidationConfig")
    private MessageValidationConfig mt798ValidationConfig;

    @Autowired
    private GenericValidationEngine genericValidationEngine;

    public ValidationResult validate(MT798 mt798) {
        ValidationResult result = new ValidationResult();
        result.setErrorMessages(new ArrayList<>());
        // Validate Field77E
        Field77E field77E = mt798.getField77E();
        if (field77E == null) {
            result.addErrorMessage(ValidationError.mustBePresent(Field77E.NAME));
            return result;
        }
        SwiftBlock4 block4 = mt798.getSwiftMessage().getBlock4();
        // Validate Section 1
        SwiftTagListBlock blockBeforeFirst77E = block4.getSubBlockBeforeFirst(Field77E.NAME, Boolean.FALSE);
        int size = result.getErrorMessages().size();
        genericValidationEngine.validate(result, mt798, blockBeforeFirst77E, mt798ValidationConfig);
        if (size != result.getErrorMessages().size()) {
            return result;
        }
        // Validate Section 2
        String subMessageType = mt798.getField12().getValue();
        SwiftTagListBlock blockAfterFirst77E = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        genericValidationEngine.validate(result, mt798, blockAfterFirst77E, subMessageType);
        return result;
    }

}
