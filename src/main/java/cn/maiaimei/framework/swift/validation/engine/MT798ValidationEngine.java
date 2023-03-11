package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.config.MT7xxValidationConfig;
import cn.maiaimei.framework.swift.exception.ValidationException;
import cn.maiaimei.framework.swift.model.mt.config.MT798Config;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ConditionalOnBean(MT7xxValidationConfig.class)
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
        String subMessageType = Optional.ofNullable(mt798.getField12()).orElseGet(Field12::new).getValue();
        if (StringUtils.isBlank(subMessageType)) {
            ValidationResult result = ValidationResult.newInstance();
            result.addErrorMessage(ValidationError.mustNotBlank(Field12.NAME));
            return result;
        }
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
     * @param indexMessageType Sub-Message Type for Index Message
     * @param subMessageType   Sub-Message Type
     * @return
     */
    public ValidationResult validate(MT798 mt798, String indexMessageType, String subMessageType) {
        Supplier<MT798Config> mt798ConfigSupplier = () -> getMT798Config(
                w -> w.getIndexMessageType().equals(indexMessageType) && w.getSubMessageType().equals(subMessageType),
                "Can't found validation config for MT 798<" + indexMessageType + "> + MT 798<" + subMessageType + ">",
                "Can't determine which validation config to use for MT 798<" + indexMessageType + "> + MT 798<" + subMessageType + ">"
        );
        return doValidate(mt798, mt798ConfigSupplier);
    }

    private ValidationResult doValidate(MT798 mt798, Supplier<MT798Config> mt798ConfigSupplier) {
        ValidationResult result = ValidationResult.newInstance();
        SwiftBlock4 block4 = mt798.getSwiftMessage().getBlock4();
        // Validate Section 1
        SwiftTagListBlock blockBeforeFirst77E = block4.getSubBlockBeforeFirst(Field77E.NAME, Boolean.TRUE);
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

    private MT798Config getMT798Config(Predicate<MT798Config> predicate,
                                       String validationConfigNotFoundError,
                                       String multipleValidationConfigError) {
        List<MT798Config> mt798Configs = mt798ConfigSet.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(mt798Configs)) {
            throw new ValidationException(validationConfigNotFoundError);
        }
        if (mt798Configs.size() > 1) {
            throw new ValidationException(multipleValidationConfigError);
        }
        return mt798Configs.get(0);
    }

}
