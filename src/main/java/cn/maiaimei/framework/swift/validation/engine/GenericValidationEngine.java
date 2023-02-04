package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import cn.maiaimei.framework.swift.validation.config.model.SequenceInfo;
import cn.maiaimei.framework.swift.validation.constants.MTXxx;
import cn.maiaimei.framework.swift.validation.validator.FieldValidatorChain;
import cn.maiaimei.framework.swift.validation.validator.MultipleComponentsFieldValidator;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;
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
    private Set<MessageValidationCfg> messageValidationCfgSet;

    @Autowired
    private FieldValidatorChain fieldValidatorChain;

    @Autowired
    private MultipleComponentsFieldValidator multipleComponentsFieldValidator;

    public void validate(ValidationResult result, SwiftTagListBlock block, String subMessageType) {
        Optional<MessageValidationCfg> messageValidationCfgOptional = messageValidationCfgSet.stream().filter(w -> w.getSubMessageType().equals(subMessageType)).findAny();
        if (!messageValidationCfgOptional.isPresent()) {
            result.addErrorMessage("Can't find validation config for MT" + subMessageType);
            return;
        }
        List<Tag> tags = block.getTags();
        MessageValidationCfg messageValidationCfg = messageValidationCfgOptional.get();
        if (CollectionUtils.isEmpty(messageValidationCfg.getSequences())) {
            List<FieldInfo> fieldInfos = messageValidationCfg.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, block);
        } else {
            List<FieldInfo> fieldInfos = messageValidationCfg.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, block);
            Map<String, List<SwiftTagListBlock>> sequenceBlockMap = getSequenceBlockMap(subMessageType, block);
            for (SequenceInfo sequenceInfo : messageValidationCfg.getSequences()) {
                String sequenceName = sequenceInfo.getSequenceName();
                List<SwiftTagListBlock> sequenceBlocks = sequenceBlockMap.get(sequenceName);
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

    @SneakyThrows
    public Map<String, List<SwiftTagListBlock>> getSequenceBlockMap(String subMessageType, SwiftTagListBlock block4) {
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
