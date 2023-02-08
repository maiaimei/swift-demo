package cn.maiaimei.framework.swift.validation.engine;

import cn.maiaimei.framework.swift.model.FieldInfo;
import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnExpression("${swift.validation.enabled:false} || ${swift.validation.enabled-mt7xx:false}")
public class MT798ValidationEngine {

    @Autowired
    private GenericValidationEngine genericValidationEngine;

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
        validateSection2(result, blockAfterFirst77E, subMessageType);
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
        field20Info.setFieldName("Transaction Reference Number");
        field20Info.setFormat("16x");
        field20Info.setStatus("M");

        FieldInfo field12Info = new FieldInfo();
        field12Info.setTag(Field12.NAME);
        field12Info.setFieldName("Sub-Message Type");
        field12Info.setFormat("3!n");
        field12Info.setStatus("M");

        List<FieldInfo> fieldInfos = new ArrayList<>();
        fieldInfos.add(field20Info);
        fieldInfos.add(field12Info);

        genericValidationEngine.validateMandatoryFields(result, fieldInfos, tags);
        genericValidationEngine.validateTags(result, fieldInfos, tags, blockBeforeFirst77E);
    }

    private void validateSection2(ValidationResult result, SwiftTagListBlock blockAfterFirst77E, String subMessageType) {
        List<Tag> tags = blockAfterFirst77E.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            result.addErrorMessage(ValidationError.mustBePresent("Section 2"));
            return;
        }
        genericValidationEngine.validate(result, blockAfterFirst77E, subMessageType);
    }
}
