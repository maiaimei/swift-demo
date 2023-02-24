package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.exception.MTSequenceProcessorNotFoundException;
import cn.maiaimei.framework.swift.exception.ValidationException;
import cn.maiaimei.framework.swift.model.mt.config.*;
import cn.maiaimei.framework.swift.processor.DefaultMTSequenceProcessor;
import cn.maiaimei.framework.swift.processor.MTSequenceProcessor;
import cn.maiaimei.framework.swift.util.SpelUtils;
import cn.maiaimei.framework.swift.util.SwiftUtils;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.mt.MTValidation;
import cn.maiaimei.framework.swift.validation.validator.FieldValidatorChain;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenericMTValidationEngine {

    private static final String LABEL_FORMAT_NO_SEQUENCE = "Field %s %s";
    private static final String LABEL_FORMAT_IN_SEQUENCE = "In sequence %s, field %s %s";

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    @Autowired
    private Set<GenericMTConfig> genericMTConfigSet;

    @Autowired
    private Set<MTSequenceProcessor> mtSequenceProcessorSet;

    @Autowired
    private DefaultMTSequenceProcessor defaultMTSequenceProcessor;

    @Autowired(required = false)
    private Map<String, MTValidation> mtValidationMap;

    public ValidationResult validate(String message) {
        SwiftMessage swiftMessage = SwiftUtils.parseToSwiftMessage(message);
        String messageType = swiftMessage.getType();
        AbstractMT mt = SwiftUtils.parseToAbstractMT(swiftMessage, messageType);
        return validate(mt, messageType);
    }

    public ValidationResult validate(String message, String messageType) {
        AbstractMT mt = SwiftUtils.parseToAbstractMT(message, messageType);
        return validate(mt, messageType);
    }

    public ValidationResult validate(String message, String messageType, MessageConfig messageConfig) {
        AbstractMT mt = SwiftUtils.parseToAbstractMT(message, messageType);
        return validate(mt, messageType, messageConfig);
    }

    public ValidationResult validate(SwiftMessage swiftMessage) {
        String messageType = swiftMessage.getType();
        AbstractMT mt = SwiftUtils.parseToAbstractMT(swiftMessage, messageType);
        return validate(mt, messageType);
    }

    public ValidationResult validate(SwiftMessage swiftMessage, String messageType) {
        AbstractMT mt = SwiftUtils.parseToAbstractMT(swiftMessage, messageType);
        return validate(mt, messageType);
    }

    public ValidationResult validate(SwiftMessage swiftMessage, String messageType, MessageConfig messageConfig) {
        AbstractMT mt = SwiftUtils.parseToAbstractMT(swiftMessage, messageType);
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
        List<MessageConfig> messageConfigs = genericMTConfigSet.stream()
                .filter(w -> w.getMessageType().equals(messageType))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(messageConfigs)) {
            throw new ValidationException("Can't found validation config for MT" + messageType);
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
        MTSequenceProcessor sequenceProcessor = getMTSequenceProcessor(messageType);
        Map<String, List<SwiftTagListBlock>> sequenceMap = sequenceProcessor.getSequenceMap(mt);
        if (CollectionUtils.isEmpty(sequenceMap)) {
            return;
        }
        for (SequenceInfo sequenceInfo : messageConfig.getSequences()) {
            String sequenceName = sequenceInfo.getName();
            List<SwiftTagListBlock> sequenceList = sequenceMap.get(sequenceName);
            if (!CollectionUtils.isEmpty(sequenceList)) {
                for (SwiftTagListBlock sequenceBlock : sequenceList) {
                    List<Tag> tags = sequenceBlock.getTags();
                    if (CollectionUtils.isEmpty(tags)) {
                        if (ValidatorUtils.isMandatory(sequenceInfo.getStatus())) {
                            result.addErrorMessage(ValidationError.mustBePresent("Sequence ".concat(sequenceName)));
                        }
                        continue;
                    }
                    List<FieldInfo> fieldInfos = sequenceInfo.getFields();
                    validateMandatoryFields(result, fieldInfos, tags, sequenceName);
                    validateFields(result, mt, sequenceBlock, tags, fieldInfos, sequenceName);
                    validateByRules(result, newStandardEvaluationContext(sequenceBlock), mt, sequenceInfo.getRules());
                }
            }
        }
    }

    private void validateMessage(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, MessageConfig messageConfig) {
        List<FieldInfo> fieldInfos = messageConfig.getFields();
        List<Tag> tags = block.getTags();
        validateMandatoryFields(result, fieldInfos, tags, StringUtils.EMPTY);
        validateFields(result, mt, block, tags, fieldInfos, StringUtils.EMPTY);
        validateByRules(result, newStandardEvaluationContext(block), mt, messageConfig.getRules());
    }

    private void validateMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, String sequenceName) {
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());
        for (FieldInfo fieldInfo : fieldInfos) {
            if (!ValidatorUtils.isMandatory(fieldInfo.getStatus())) {
                continue;
            }
            String tag = fieldInfo.getTag();
            if (!tagNames.contains(tag)) {
                String label = getLabel(sequenceName, tag, fieldInfo.getFieldName());
                result.addErrorMessage(ValidationError.mustBePresent(label));
            }
        }
    }

    private void validateFields(ValidationResult result, AbstractMT mt, SwiftTagListBlock block, List<Tag> tags, List<FieldInfo> fieldInfos, String sequenceName) {
        StandardEvaluationContext context = newStandardEvaluationContext(block);
        for (Tag tag : tags) {
            String tagName = tag.getName();
            String tagValue = tag.getValue();
            Field field = block.getFieldByName(tagName);
            Optional<FieldInfo> fieldInfoOptional = fieldInfos.stream().filter(w -> w.getTag().equals(tagName)).findAny();
            if (!fieldInfoOptional.isPresent()) {
                continue;
            }
            FieldInfo fieldInfo = fieldInfoOptional.get();
            String label = getLabel(sequenceName, tagName, fieldInfo.getFieldName());
            fieldValidatorChain.handleValidation(result, fieldInfo, field, label, tagValue);
            validateByRules(result, context, mt, fieldInfo.getRules());
        }
    }

    private void validateByRules(ValidationResult result, StandardEvaluationContext context, AbstractMT mt, List<RuleInfo> ruleInfos) {
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
                MTValidation mtValidation = null;
                if (mtValidationMap != null) {
                    mtValidation = mtValidationMap.get(ruleInfo.getBeanName());
                }
                if (mtValidation != null) {
                    mtValidation.validate(result, mt);
                }
            }
        }
    }

    private String getLabel(String sequenceName, String tagName, String fieldName) {
        if (StringUtils.isBlank(sequenceName)) {
            return String.format(LABEL_FORMAT_NO_SEQUENCE, tagName, fieldName);
        }
        return String.format(LABEL_FORMAT_IN_SEQUENCE, sequenceName, tagName, fieldName);
    }

    private MTSequenceProcessor getMTSequenceProcessor(String messageType) {
        if (StringUtils.isBlank(messageType)) {
            return defaultMTSequenceProcessor;
        }
        for (MTSequenceProcessor processor : mtSequenceProcessorSet) {
            if (processor.supportsMessageType(messageType)) {
                return processor;
            }
        }
        throw new MTSequenceProcessorNotFoundException(messageType);
    }

    private StandardEvaluationContext newStandardEvaluationContext(SwiftTagListBlock block) {
        return SpelUtils.newStandardEvaluationContext("block", block);
    }

}
