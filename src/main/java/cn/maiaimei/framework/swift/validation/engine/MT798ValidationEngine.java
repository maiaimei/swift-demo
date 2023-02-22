package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.exception.ValidationException;
import cn.maiaimei.framework.swift.model.mt.config.MT798Config;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: refactor: MT798ValidationEngine
 */
@Component
public class MT798ValidationEngine {

    @Autowired
    private Set<MT798Config> mt798ConfigSet;

    @Autowired
    private MT798Config mt798Section1Config;

    @Autowired
    private GenericMTValidationEngine genericMTValidationEngine;

    /**
     * validate corporate-to-bank message
     */
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
        genericMTValidationEngine.validate(result, mt798, blockBeforeFirst77E, mt798Section1Config);
        if (size != result.getErrorMessages().size()) {
            return result;
        }
        // Validate Section 2
        String subMessageType = mt798.getField12().getValue();
        SwiftTagListBlock blockAfterFirst77E = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        List<MT798Config> mt798Configs = mt798ConfigSet.stream()
                .filter(w -> w.getSubMessageType().equals(subMessageType) && Boolean.TRUE.equals(w.getCorporateToBank()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(mt798Configs)) {
            throw new ValidationException("Can't found validation config for MT" + subMessageType);
        }
        if (mt798Configs.size() > 1) {
            throw new ValidationException("Can't determine which validation config to use for MT" + subMessageType);
        }
        MT798Config config = mt798Configs.get(0);
        genericMTValidationEngine.validate(result, mt798, blockAfterFirst77E, config, subMessageType);
        return result;
    }

    /**
     * validate bank-to-corporate message
     */
    public ValidationResult validate(MT798 mt798, String indexMessageType, String subMessageType) {
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
        genericMTValidationEngine.validate(result, mt798, blockBeforeFirst77E, mt798Section1Config);
        if (size != result.getErrorMessages().size()) {
            return result;
        }
        // Validate Section 2
        SwiftTagListBlock blockAfterFirst77E = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        List<MT798Config> mt798Configs = mt798ConfigSet.stream()
                .filter(w -> w.getIndexMessageType().equals(indexMessageType) && w.getSubMessageType().equals(subMessageType))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(mt798Configs)) {
            throw new ValidationException("Can't found validation config, indexMessageType: " + indexMessageType + ", subMessageType: " + subMessageType);
        }
        if (mt798Configs.size() > 1) {
            throw new ValidationException("Can't determine which validation config to use, indexMessageType: " + indexMessageType + ", subMessageType: " + subMessageType);
        }
        MT798Config config = mt798Configs.get(0);
        genericMTValidationEngine.validate(result, mt798, blockAfterFirst77E, config, subMessageType);
        return result;
    }

}
