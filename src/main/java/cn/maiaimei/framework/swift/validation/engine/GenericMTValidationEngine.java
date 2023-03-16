package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.exception.ProcessorNotFoundException;
import cn.maiaimei.framework.swift.exception.ValidationException;
import cn.maiaimei.framework.swift.model.mt.config.*;
import cn.maiaimei.framework.swift.processor.DefaultMessageSequenceProcessor;
import cn.maiaimei.framework.swift.processor.MessageSequenceProcessor;
import cn.maiaimei.framework.swift.util.SpelUtils;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.mt.MessageValidation;
import cn.maiaimei.framework.swift.validation.validator.FieldValidatorChain;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenericMTValidationEngine {

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    @Autowired(required = false)
    private Set<GenericMTConfig> genericMTConfigSet;

    @Autowired
    private Set<MessageSequenceProcessor> messageSequenceProcessorSet;

    @Autowired
    private DefaultMessageSequenceProcessor defaultMTSequenceProcessor;

    @Autowired(required = false)
    private Map<String, MessageValidation> messageValidationMap;

    @SneakyThrows
    public ValidationResult validate(String message) {
        AbstractMT mt = AbstractMT.parse(message);
        return validate(mt, mt.getMessageType());
    }

    @SneakyThrows
    public ValidationResult validate(String message, String messageType) {
        AbstractMT mt = AbstractMT.parse(message);
        return validate(mt, messageType);
    }

    @SneakyThrows
    public ValidationResult validate(String message, String messageType, MessageConfig messageConfig) {
        AbstractMT mt = AbstractMT.parse(message);
        return validate(mt, messageType, messageConfig);
    }

    public ValidationResult validate(AbstractMT mt, String messageType) {
        ValidationResult result = ValidationResult.newInstance();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        validate(result, mt, block4, messageType);
        return result;
    }

    public ValidationResult validate(AbstractMT mt, String messageType, MessageConfig messageConfig) {
        ValidationResult result = ValidationResult.newInstance();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        validate(result, mt, block4, messageConfig, messageType);
        return result;
    }

    public void validate(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, String messageType) {
        if (StringUtils.isBlank(messageType)) {
            result.addErrorMessage(ValidationError.mustNotBlank("messageType"));
            return;
        }
        if (CollectionUtils.isEmpty(genericMTConfigSet)) {
            throwValidationConfigNotFoundException(messageType);
            return;
        }
        List<MessageConfig> messageConfigs = genericMTConfigSet.stream()
                .filter(w -> w.getMessageType().equals(messageType))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(messageConfigs)) {
            throwValidationConfigNotFoundException(messageType);
            return;
        }
        if (messageConfigs.size() > 1) {
            throw new ValidationException("Can't determine which validation config to use for MT" + messageType);
        }
        MessageConfig messageConfig = messageConfigs.get(0);
        validate(result, mt, block, messageConfig, messageType);
    }

    public void validate(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, MessageConfig messageConfig) {
        validate(result, mt, block, messageConfig, StringUtils.EMPTY);
    }

    public void validate(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, MessageConfig messageConfig, String messageType) {
        if (CollectionUtils.isEmpty(messageConfig.getSequences())) {
            validateMessage(result, mt, block, messageConfig);
        } else {
            validateSequenceMessage(result, mt, block, messageConfig, messageType);
        }
    }

    private void validateSequenceMessage(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, MessageConfig messageConfig, String messageType) {
        validateMessage(result, mt, block, messageConfig);
        MessageSequenceProcessor sequenceProcessor = getMessageSequenceProcessor(messageType);
        Map<String, List<SwiftTagListBlock>> sequenceMap = sequenceProcessor.getSequenceMap(mt);
        if (CollectionUtils.isEmpty(sequenceMap)) {
            result.addErrorMessage("Can't found sequence block for MT" + messageType + ", please configure bean which must implements MTSequenceProcessor");
            return;
        }
        for (SequenceInfo sequenceInfo : messageConfig.getSequences()) {
            String sequenceName = sequenceInfo.getName();
            List<SwiftTagListBlock> sequenceList = sequenceMap.get(sequenceName);
            if (CollectionUtils.isEmpty(sequenceList)) {
                continue;
            }
            for (SwiftTagListBlock sequenceBlock : sequenceList) {
                List<Tag> tags = sequenceBlock.getTags();
                if (CollectionUtils.isEmpty(tags)) {
                    if (ValidatorUtils.isMandatory(sequenceInfo.getStatus())) {
                        result.addErrorMessage(ValidationError.mustBePresent("Sequence ".concat(sequenceName)));
                    }
                    continue;
                }
                List<FieldInfo> fieldInfos = sequenceInfo.getFields();
                validateFields(result, mt, sequenceBlock, tags, fieldInfos, sequenceName);
                int errorMessageCount = result.getErrorMessages().size();
                if (errorMessageCount == result.getErrorMessages().size()) {
                    validateFieldsByRule(result, newStandardEvaluationContext(sequenceBlock), mt, sequenceInfo.getRules());
                }
            }
        }
    }

    private void validateMessage(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, MessageConfig messageConfig) {
        List<FieldInfo> fieldInfos = messageConfig.getFields();
        List<Tag> tags = block.getTags();
        int errorMessageCount = result.getErrorMessages().size();
        validateFields(result, mt, block, tags, fieldInfos, StringUtils.EMPTY);
        if (errorMessageCount != result.getErrorMessages().size()) {
            return;
        }
        validateFieldsByRule(result, newStandardEvaluationContext(block), mt, messageConfig.getRules());
    }

    private void validateFields(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, List<Tag> tags, List<FieldInfo> fieldInfos, String sequenceName) {
        StandardEvaluationContext context = newStandardEvaluationContext(block);
        for (FieldInfo fieldInfo : fieldInfos) {
            String tagName = fieldInfo.getTag();
            String tagValue = block.getTagValue(tagName);
            String label = getLabel(sequenceName, tagName);
            Field field = block.getFieldByName(tagName);
            int errorMessageCount = result.getErrorMessages().size();
            fieldValidatorChain.handleValidation(result, fieldInfo, field, label, tagValue);
            if (errorMessageCount == result.getErrorMessages().size()) {
                validateFieldsByRule(result, context, mt, fieldInfo.getRules());
            }
        }
    }

    private void validateFieldsByRule(ValidationResult result, StandardEvaluationContext context, AbstractMT mt, List<RuleInfo> ruleInfos) {
        if (CollectionUtils.isEmpty(ruleInfos)) {
            return;
        }
        for (RuleInfo ruleInfo : ruleInfos) {
            if (StringUtils.isNotBlank(ruleInfo.getExpressionString())
                    && SpelUtils.parseExpressionAsBoolean(context, ruleInfo.getExpressionString())) {
                result.addErrorMessage(ruleInfo.getErrorMessage());
                break;
            }
            if (StringUtils.isNotBlank(ruleInfo.getBeanName())) {
                MessageValidation messageValidation = null;
                if (messageValidationMap != null) {
                    messageValidation = messageValidationMap.get(ruleInfo.getBeanName());
                }
                if (messageValidation != null) {
                    messageValidation.validate(result, mt);
                }
            }
        }
    }

    private String getLabel(String sequenceName, String tagName) {
        if (StringUtils.isBlank(sequenceName)) {
            return tagName;
        }
        return String.format("In sequence %s, %s", sequenceName, tagName);
    }

    private MessageSequenceProcessor getMessageSequenceProcessor(String messageType) {
        if (StringUtils.isBlank(messageType)) {
            return defaultMTSequenceProcessor;
        }
        for (MessageSequenceProcessor processor : messageSequenceProcessorSet) {
            if (processor.supportsMessageType(messageType)) {
                return processor;
            }
        }
        throw new ProcessorNotFoundException("Can't found MTSequenceProcessor for MT" + messageType);
    }

    private StandardEvaluationContext newStandardEvaluationContext(SwiftTagListBlock block) {
        return SpelUtils.newStandardEvaluationContext("block", block);
    }

    private void throwValidationConfigNotFoundException(String messageType) {
        throw new ValidationException("Can't found validation config for MT" + messageType + ", please check whether the configuration file exists, or check whether validation is enabled");
    }

}
