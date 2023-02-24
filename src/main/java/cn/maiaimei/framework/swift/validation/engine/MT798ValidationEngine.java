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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * TODO: refactor: MT798ValidationEngine
 */
@Component
public class MT798ValidationEngine {

    @Autowired
    private GenericMTValidationEngine genericMTValidationEngine;

    @Autowired
    private MT798Config mt798Section1Config;

    @Autowired
    private Set<MT798Config> mt798ConfigSet;

    /**
     * validate message for the corporate-to-bank flows
     */
    public ValidationResult validate(MT798 mt798) {
        String subMessageType = mt798.getField12().getValue();
        Supplier<MT798Config> mt798ConfigSupplier = () -> getMT798Config(
                w -> w.getSubMessageType().equals(subMessageType) && Boolean.TRUE.equals(w.getCorporateToBank()),
                "Can't found validation config for MT 798<" + subMessageType + ">",
                "Can't determine which validation config to use for MT 798<" + subMessageType + ">"
        );
        return doValidate(mt798, mt798ConfigSupplier);
    }

    /**
     * validate message by Sub-Message Type
     *
     * @param mt798
     * @param indexMessageType Index Message Sub-Message Type
     * @param subMessageType   Sub-Message Type
     * @return
     */
    public ValidationResult validate(MT798 mt798, String indexMessageType, String subMessageType) {
        Supplier<MT798Config> mt798ConfigSupplier = () -> getMT798Config(
                w -> w.getIndexMessageType().equals(indexMessageType) && w.getSubMessageType().equals(subMessageType),
                "Can't found validation config, Index Message is MT 798<" + indexMessageType + ">, Message is MT 798<" + subMessageType + ">",
                "Can't determine which validation config to use, Index Message is MT 798<" + indexMessageType + ">, Message is MT 798<" + subMessageType + ">"
        );
        return doValidate(mt798, mt798ConfigSupplier);
    }

    private ValidationResult doValidate(MT798 mt798, Supplier<MT798Config> mt798ConfigSupplier) {
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
        int errorMessageCount = result.getErrorMessages().size();
        genericMTValidationEngine.validate(result, mt798, blockBeforeFirst77E, mt798Section1Config);
        if (errorMessageCount != result.getErrorMessages().size()) {
            return result;
        }
        // Validate Section 2
        String subMessageType = mt798.getField12().getValue();
        SwiftTagListBlock blockAfterFirst77E = block4.getSubBlockAfterFirst(Field77E.NAME, Boolean.FALSE);
        MT798Config config = mt798ConfigSupplier.get();
        genericMTValidationEngine.validate(result, mt798, blockAfterFirst77E, config, subMessageType);
        return result;
    }

    private MT798Config getMT798Config(Predicate<MT798Config> predicate, String validationConfigNotFound, String multipleValidationConfig) {
        List<MT798Config> mt798Configs = mt798ConfigSet.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(mt798Configs)) {
            throw new ValidationException(validationConfigNotFound);
        }
        if (mt798Configs.size() > 1) {
            throw new ValidationException(multipleValidationConfig);
        }
        return mt798Configs.get(0);
    }

}
