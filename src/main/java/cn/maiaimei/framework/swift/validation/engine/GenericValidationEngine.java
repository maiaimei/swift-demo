package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.model.FieldInfo;
import cn.maiaimei.framework.swift.model.MessageInfo;
import cn.maiaimei.framework.swift.model.SequenceInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import cn.maiaimei.framework.swift.validation.config.ValidationConfig;
import cn.maiaimei.framework.swift.validation.constants.MTXxx;
import cn.maiaimei.framework.swift.validation.validator.FieldValidatorChain;
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

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    public ValidationResult validate(String message, String messageType) {
        ValidationResult result = getValidationResult();
        SwiftMessage swiftMessage = getSwiftMessage(message);
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validate(result, block4, messageType);
        return result;
    }

    public ValidationResult validate(String message, String messageType, MessageInfo messageInfo) {
        ValidationResult result = getValidationResult();
        SwiftMessage swiftMessage = getSwiftMessage(message);
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validate(result, block4, messageType, messageInfo);
        return result;
    }

    public ValidationResult validate(SwiftMessage swiftMessage, String messageType) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validate(result, block4, messageType);
        return result;
    }

    public ValidationResult validate(SwiftMessage swiftMessage, String messageType, MessageInfo messageInfo) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        validate(result, block4, messageType, messageInfo);
        return result;
    }

    public ValidationResult validate(AbstractMT mt, String messageType) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        validate(result, block4, messageType);
        return result;
    }

    public ValidationResult validate(AbstractMT mt, String messageType, MessageInfo messageInfo) {
        ValidationResult result = getValidationResult();
        SwiftBlock4 block4 = mt.getSwiftMessage().getBlock4();
        validate(result, block4, messageType, messageInfo);
        return result;
    }

    public void validate(ValidationResult result, SwiftTagListBlock block, String messageType) {
        List<MessageInfo> cfgList = ValidationConfig.MESSAGE_VALIDATION_CONFIG_LIST.stream().filter(w -> w.getMessageType().equals(messageType)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cfgList)) {
            result.addErrorMessage("Can't find validation config for MT" + messageType);
            return;
        }
        if (cfgList.size() > 1) {
            result.addErrorMessage("Can't determine which validation config to use for MT" + messageType);
            return;
        }
        MessageInfo messageInfo = cfgList.get(0);
        validate(result, block, messageType, messageInfo);
    }

    private void validate(ValidationResult result, SwiftTagListBlock block, String messageType, MessageInfo messageInfo) {
        List<Tag> tags = block.getTags();
        if (CollectionUtils.isEmpty(messageInfo.getSequences())) {
            List<FieldInfo> fieldInfos = messageInfo.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, block);
        } else {
            List<FieldInfo> fieldInfos = messageInfo.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, block);
            Map<String, List<SwiftTagListBlock>> sequenceBlockMap = getSequenceBlockMap(messageType, block);
            for (SequenceInfo sequenceInfo : messageInfo.getSequences()) {
                String sequenceName = sequenceInfo.getName();
                List<SwiftTagListBlock> sequenceBlocks = sequenceBlockMap.get(sequenceName);
                if (!CollectionUtils.isEmpty(sequenceBlocks)) {
                    for (SwiftTagListBlock sequenceBlock : sequenceBlocks) {
                        List<Tag> sequenceTags = sequenceBlock.getTags();
                        if (ValidatorUtils.isMandatory(sequenceInfo.getStatus()) && CollectionUtils.isEmpty(sequenceTags)) {
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

    protected void validateMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags) {
        validateSequenceMandatoryFields(result, fieldInfos, tags, StringUtils.EMPTY);
    }

    protected void validateTags(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, SwiftTagListBlock block) {
        validateSequenceTags(result, fieldInfos, tags, block, StringUtils.EMPTY);
    }

    protected void validateSequenceMandatoryFields(ValidationResult result, List<FieldInfo> fieldInfos, List<Tag> tags, String sequenceName) {
        List<String> tagNames = tags.stream().map(Tag::getName).collect(Collectors.toList());
        for (FieldInfo fieldInfo : fieldInfos) {
            if (ValidatorUtils.isMandatory(fieldInfo.getStatus())) {
                continue;
            }
            String tag = fieldInfo.getTag();
            if (!tagNames.contains(tag)) {
                String label = getLabel(sequenceName, tag, fieldInfo.getFieldName());
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
            String label = getLabel(sequenceName, tagName, fieldInfo.getFieldName());
            fieldValidatorChain.doValidation(result, fieldInfo, field, label, tagValue);
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

    private String getLabel(String sequenceName, String tagName, String fieldLabel) {
        if (StringUtils.isBlank(sequenceName)) {
            return tagName.concat(StringUtils.SPACE).concat(fieldLabel);
        }
        return String.format(IN_SEQUENCE, sequenceName)
                .concat(tagName).concat(StringUtils.SPACE).concat(fieldLabel);
    }

}
