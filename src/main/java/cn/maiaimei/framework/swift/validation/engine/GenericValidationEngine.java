package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.ValidationConfigUtils;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import cn.maiaimei.framework.swift.validation.config.model.SequenceInfo;
import cn.maiaimei.framework.swift.validation.constants.MTXxx;
import cn.maiaimei.framework.swift.validation.validator.FieldValidatorChain;
import cn.maiaimei.framework.swift.validation.validator.MultipleComponentsFieldValidator;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GenericValidationEngine {

    private static final String GET_SEQUENCE = "getSequence";
    private static final String IN_SEQUENCE = "In Sequence %s, ";

    private static final Map<String, String> MESSAGE_CATAGORY_TYPE = new HashMap<>();

    static {
        MESSAGE_CATAGORY_TYPE.put("1", "mt1xx");
        MESSAGE_CATAGORY_TYPE.put("2", "mt2xx");
        MESSAGE_CATAGORY_TYPE.put("3", "mt3xx");
        MESSAGE_CATAGORY_TYPE.put("4", "mt4xx");
        MESSAGE_CATAGORY_TYPE.put("5", "mt5xx");
        MESSAGE_CATAGORY_TYPE.put("6", "mt6xx");
        MESSAGE_CATAGORY_TYPE.put("7", "mt7xx");
        MESSAGE_CATAGORY_TYPE.put("8", "mt8xx");
        MESSAGE_CATAGORY_TYPE.put("9", "mt9xx");
    }

    private static final Object EXTEND_MESSAGE_VALIDATION_CFG_LOCK = new Object();

    private static final Map<String, MessageValidationCfg> EXTEND_MESSAGE_VALIDATION_CFG_MAP = new HashMap<>();

    @Autowired
    private Set<MessageValidationCfg> messageValidationCfgSet;

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    @Autowired
    private MultipleComponentsFieldValidator multipleComponentsFieldValidator;

    public ValidationResult validate(String message, String messageType) {
        ValidationResult result = getValidationResult();
        SwiftMessage swiftMessage = getSwiftMessage(message);
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validate(result, block4, messageType);
        return result;
    }

    public ValidationResult validateByConfigLocation(String message, String messageType, String configLocation) {
        ValidationResult result = getValidationResult();
        SwiftMessage swiftMessage = getSwiftMessage(message);
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validateByConfigLocation(result, block4, messageType, configLocation);
        return result;
    }

    public ValidationResult validateByConfigFileName(String message, String messageType, String configFileName) {
        ValidationResult result = getValidationResult();
        SwiftMessage swiftMessage = getSwiftMessage(message);
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        String messageCatagory = getMessageCatagory(messageType);
        validateByConfigFileName(result, block4, messageCatagory, messageType, configFileName);
        return result;
    }

    public ValidationResult validate(SwiftMessage swiftMessage, String messageType) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validate(result, block4, messageType);
        return result;
    }

    public ValidationResult validateByConfigLocation(SwiftMessage swiftMessage, String messageType, String configLocation) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validateByConfigLocation(result, block4, messageType, configLocation);
        return result;
    }

    public ValidationResult validateByConfigFileName(SwiftMessage swiftMessage, String messageType, String configFileName) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        String messageCatagory = getMessageCatagory(messageType);
        validateByConfigFileName(result, block4, messageCatagory, messageType, configFileName);
        return result;
    }

    public ValidationResult validate(AbstractMT mt, String messageType) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        validate(result, block4, messageType);
        return result;
    }

    public ValidationResult validateByConfigLocation(AbstractMT mt, String messageType, String configLocation) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        validateByConfigLocation(result, block4, messageType, configLocation);
        return result;
    }

    public ValidationResult validateByConfigFileName(AbstractMT mt, String messageType, String configFileName) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        String messageCatagory = getMessageCatagory(messageType);
        validateByConfigFileName(result, block4, messageCatagory, messageType, configFileName);
        return result;
    }

    public void validate(ValidationResult result, SwiftTagListBlock block, String messageType) {
        List<MessageValidationCfg> cfgList = messageValidationCfgSet.stream().filter(w -> w.getMessageType().equals(messageType)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cfgList)) {
            result.addErrorMessage("Can't find validation config for MT" + messageType);
            return;
        }
        if (cfgList.size() > 1) {
            result.addErrorMessage("Can't determine which validation config to use for MT" + messageType);
            return;
        }
        MessageValidationCfg messageValidationCfg = cfgList.get(0);
        validate(result, block, messageType, messageValidationCfg);
    }

    public void validateByConfigLocation(ValidationResult result, SwiftTagListBlock block, String messageType, String configLocation) {
        MessageValidationCfg messageValidationCfg = getMessageValidationCfg(configLocation);
        validate(result, block, messageType, messageValidationCfg);
    }

    public void validateByConfigFileName(ValidationResult result, SwiftTagListBlock block, String messageCatagory, String messageType, String configFileName) {
        MessageValidationCfg messageValidationCfg = null;
        for (MessageValidationCfg cfg : messageValidationCfgSet) {
            String beanName = ValidationConfigUtils.getValidationConfigBeanName(messageCatagory, configFileName);
            if (cfg.getClass().getSimpleName().equals(beanName)) {
                messageValidationCfg = cfg;
                break;
            }
        }
        if (messageValidationCfg == null) {
            result.addErrorMessage("Can't find validation config for MT" + messageType);
            return;
        }
        validate(result, block, messageType, messageValidationCfg);
    }

    private void validate(ValidationResult result, SwiftTagListBlock block, String messageType, MessageValidationCfg messageValidationCfg) {
        List<Tag> tags = block.getTags();
        if (CollectionUtils.isEmpty(messageValidationCfg.getSequences())) {
            List<FieldInfo> fieldInfos = messageValidationCfg.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, block);
        } else {
            List<FieldInfo> fieldInfos = messageValidationCfg.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, block);
            Map<String, List<SwiftTagListBlock>> sequenceBlockMap = getSequenceBlockMap(messageType, block);
            for (SequenceInfo sequenceInfo : messageValidationCfg.getSequences()) {
                String sequenceName = sequenceInfo.getSequenceName();
                List<SwiftTagListBlock> sequenceBlocks = sequenceBlockMap.get(sequenceName);
                if (!CollectionUtils.isEmpty(sequenceBlocks)) {
                    for (SwiftTagListBlock sequenceBlock : sequenceBlocks) {
                        List<Tag> sequenceTags = sequenceBlock.getTags();
                        if (sequenceInfo.isMandatory() && CollectionUtils.isEmpty(sequenceTags)) {
                            result.addErrorMessage(ValidationError.mustBePresent("Sequence ".concat(sequenceName)));
                            continue;
                        }
                        List<FieldInfo> sequenceFieldInfos = sequenceInfo.getFields();
                        validateSequenceMandatoryFields(result, sequenceFieldInfos, sequenceTags, sequenceName);
                        validateSequenceTags(result, sequenceFieldInfos, sequenceTags, sequenceBlock, sequenceName);
                    }
                }
            }
        }
    }

    private ValidationResult getValidationResult() {
        ValidationResult result = new ValidationResult();
        result.setErrorMessages(new ArrayList<>());
        return result;
    }

    @SneakyThrows
    private SwiftMessage getSwiftMessage(String message) {
        SwiftParser parser = new SwiftParser(message);
        return parser.message();
    }

    private String getMessageCatagory(String messageType) {
        String firstNumber = messageType.substring(0, 2);
        return MESSAGE_CATAGORY_TYPE.get(firstNumber);
    }

    private MessageValidationCfg getMessageValidationCfg(String configLocation) {
        if (EXTEND_MESSAGE_VALIDATION_CFG_MAP.get(configLocation) == null) {
            synchronized (EXTEND_MESSAGE_VALIDATION_CFG_LOCK) {
                if (EXTEND_MESSAGE_VALIDATION_CFG_MAP.get(configLocation) == null) {
                    MessageValidationCfg messageValidationCfg = ValidationConfigUtils.getMessageValidationCfg(configLocation);
                    EXTEND_MESSAGE_VALIDATION_CFG_MAP.put(configLocation, messageValidationCfg);
                    return messageValidationCfg;
                }
                return EXTEND_MESSAGE_VALIDATION_CFG_MAP.get(configLocation);
            }
        }
        return EXTEND_MESSAGE_VALIDATION_CFG_MAP.get(configLocation);
    }

    @SneakyThrows
    private Map<String, List<SwiftTagListBlock>> getSequenceBlockMap(String subMessageType, SwiftTagListBlock block4) {
        Map<String, List<SwiftTagListBlock>> sequenceBlockMap = new HashMap<>();
        String className = MTXxx.CLASS_NAME_MAP.get(subMessageType);
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.newInstance();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String declaredMethodName = declaredMethod.getName();
            if (declaredMethodName.startsWith(GET_SEQUENCE)) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                if (parameterTypes.length == 1
                        && SwiftTagListBlock.class.getSimpleName().equals(parameterTypes[0].getSimpleName())) {
                    Object result = declaredMethod.invoke(instance, block4);
                    List<SwiftTagListBlock> blocks;
                    if (result instanceof List) {
                        blocks = (List<SwiftTagListBlock>) result;
                    } else {
                        blocks = Collections.singletonList((SwiftTagListBlock) result);
                    }
                    sequenceBlockMap.put(declaredMethodName.replaceAll(GET_SEQUENCE, StringUtils.EMPTY), blocks);
                }
            }
        }
        return sequenceBlockMap;
    }

    protected void validateMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags) {
        validateSequenceMandatoryFields(result, fieldInfos, tags, StringUtils.EMPTY);
    }

    protected void validateTags(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, SwiftTagListBlock block) {
        validateSequenceTags(result, fieldInfos, tags, block, StringUtils.EMPTY);
    }

    protected void validateSequenceMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, String sequenceName) {
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());
        for (FieldInfo fieldInfo : fieldInfos) {
            if (!fieldInfo.isMandatory()) {
                continue;
            }
            String tag = fieldInfo.getTag();
            if (!tagNames.contains(tag)) {
                String label = getLabel(sequenceName, tag, fieldInfo.getLabel());
                result.addErrorMessage(ValidationError.mustBePresent(label));
            }
        }
    }

    protected void validateSequenceTags(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, SwiftTagListBlock block, String sequenceName) {
        for (Tag tag : tags) {
            String tagName = tag.getName();
            String tagValue = tag.getValue();
            Field field = block.getFieldByName(tagName);
            Optional<FieldInfo> fieldInfoOptional = fieldInfos.stream().filter(w -> w.getTag().equals(tagName)).findAny();
            if (!fieldInfoOptional.isPresent()) {
                continue;
            }
            FieldInfo fieldInfo = fieldInfoOptional.get();
            String label = getLabel(sequenceName, tagName, fieldInfo.getLabel());
            fieldValidatorChain.doValidation(result, fieldInfo, field, label, tagValue);
            multipleComponentsFieldValidator.validate(result, fieldInfo, field, label, tagValue);
        }
    }

    private String getLabel(String sequenceName, String tagName, String fieldLabel) {
        if (StringUtils.isBlank(sequenceName)) {
            return tagName.concat(StringUtils.SPACE).concat(fieldLabel);
        }
        return String.format(IN_SEQUENCE, sequenceName)
                .concat(tagName).concat(StringUtils.SPACE).concat(fieldLabel);
    }

}
