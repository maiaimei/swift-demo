package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.model.mt.config.MessageConfig;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationEngine {
    @Autowired
    private GenericMTValidationEngine genericMTValidationEngine;

    @Autowired(required = false)
    private MT798ValidationEngine mt798ValidationEngine;

    public ValidationResult validate(String message) {
        return genericMTValidationEngine.validate(message);
    }

    public ValidationResult validate(String message, String messageType) {
        return genericMTValidationEngine.validate(message, messageType);
    }

    public ValidationResult validate(String message, String messageType, MessageConfig messageConfig) {
        return genericMTValidationEngine.validate(message, messageType, messageConfig);
    }

    public ValidationResult validate(AbstractMT mt, String messageType) {
        return genericMTValidationEngine.validate(mt, messageType);
    }

    public ValidationResult validate(AbstractMT mt, String messageType, MessageConfig messageConfig) {
        return genericMTValidationEngine.validate(mt, messageType, messageConfig);
    }

    /**
     * validate message for the corporate-to-bank flows
     */
    public ValidationResult validate(MT798 mt798) {
        return mt798ValidationEngine.validate(mt798);
    }

    /**
     * validate message by Sub-Message Type
     *
     * @param mt798
     * @param indexMessageType Sub-Message Type for Index Message
     * @param subMessageType   Sub-Message Type
     * @return
     */
    public ValidationResult validate(MT798 mt798, String indexMessageType, String subMessageType) {
        return mt798ValidationEngine.validate(mt798, indexMessageType, subMessageType);
    }

    /**
     * validate message by Sub-Message Type
     *
     * @param mt798
     * @param indexMessageType
     * @return
     */
    public ValidationResult validate(MT798 mt798, String indexMessageType) {
        return mt798ValidationEngine.validate(mt798, indexMessageType, indexMessageType);
    }
}
