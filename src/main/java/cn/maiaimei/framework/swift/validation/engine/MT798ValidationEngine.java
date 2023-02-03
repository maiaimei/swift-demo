package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import cn.maiaimei.framework.swift.validation.config.model.SequenceInfo;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT760;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class MT798ValidationEngine extends AbstractValidationEngine {

    @Autowired
    private Set<MessageValidationCfg> messageValidationCfgSet;

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
        SwiftTagListBlock blockBeforeFirst77E = block4.getSubBlockBeforeFirst(Field77E.NAME, false);
        int size = result.getErrorMessages().size();
        validateSection1(result, blockBeforeFirst77E);
        if (size != result.getErrorMessages().size()) {
            return result;
        }
        // Validate Section 2
        String subMessageType = mt798.getField12().getValue();
        SwiftTagListBlock blockAfterFirst77E = block4.getSubBlockAfterFirst(Field77E.NAME, false);
        validateSection2(result, mt798, blockAfterFirst77E, subMessageType);
        return result;
    }

    private void validateSection1(ValidationResult result, SwiftTagListBlock blockBeforeFirst77E) {
        List<Tag> tags = blockBeforeFirst77E.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            result.addErrorMessage(ValidationError.mustBePresent("Section 1"));
            return;
        }

        FieldInfo field20Info = new FieldInfo();
        field20Info.setTag(Field20.NAME);
        field20Info.setLabel("Transaction Reference Number");
        field20Info.setFormat("16x");
        field20Info.setMandatory(true);

        FieldInfo field12Info = new FieldInfo();
        field12Info.setTag(Field12.NAME);
        field12Info.setLabel("Sub-Message Type");
        field12Info.setFormat("3!n");
        field12Info.setMandatory(true);

        List<FieldInfo> fieldInfos = new ArrayList<>();
        fieldInfos.add(field20Info);
        fieldInfos.add(field12Info);

        validateMandatoryFields(result, fieldInfos, tags);
        validateTags(result, fieldInfos, tags, blockBeforeFirst77E);
    }

    private void validateSection2(ValidationResult result, MT798 mt798, SwiftTagListBlock blockAfterFirst77E, String subMessageType) {
        List<Tag> tags = blockAfterFirst77E.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            result.addErrorMessage(ValidationError.mustBePresent("Section 2"));
            return;
        }
        Optional<MessageValidationCfg> messageValidationCfgOptional = messageValidationCfgSet.stream().filter(w -> w.getSubMessageType().equals(subMessageType)).findAny();
        if (!messageValidationCfgOptional.isPresent()) {
            result.addErrorMessage("Can't find validation config for MT" + subMessageType);
            return;
        }
        MessageValidationCfg messageValidationCfg = messageValidationCfgOptional.get();
        if (CollectionUtils.isEmpty(messageValidationCfg.getSequences())) {
            List<FieldInfo> fieldInfos = messageValidationCfg.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, blockAfterFirst77E);
        } else {
            List<FieldInfo> fieldInfos = messageValidationCfg.getFields();
            validateMandatoryFields(result, fieldInfos, tags);
            validateTags(result, fieldInfos, tags, blockAfterFirst77E);
            Map<String, SwiftTagListBlock> sequenceBlockMap = getSwiftTagListBlocks(mt798, subMessageType);
            for (SequenceInfo sequenceInfo : messageValidationCfg.getSequences()) {
                String sequenceName = sequenceInfo.getSequenceName();
                SwiftTagListBlock sequenceBlock = sequenceBlockMap.get(sequenceName);
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

    private Map<String, SwiftTagListBlock> getSwiftTagListBlocks(MT798 mt798, String subMessageType) {
        switch (subMessageType) {
            case "760":
                return getMT760SwiftTagListBlocks(mt798);
            default:
                return Collections.emptyMap();
        }
    }

    private Map<String, SwiftTagListBlock> getMT760SwiftTagListBlocks(MT798 mt798) {
        MT760 mt760 = MT760.parse(mt798.message());
        Map<String, SwiftTagListBlock> map = new HashMap<>();
        map.put("A", mt760.getSequenceA());
        map.put("B", mt760.getSequenceB());
        map.put("C", mt760.getSequenceC());
        return map;
    }
}
