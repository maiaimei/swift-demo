package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.ValidationError;
import cn.maiaimei.framework.swift.validation.config.model.FieldInfo;
import org.apache.commons.lang3.StringUtils;

// TODO: generate errorField in ValidationEngine
@Deprecated
public abstract class AbstractFieldValidator implements FieldValidator {
    /**
     * Generate label by sequenceName, tag and fieldName.
     * <p>For example:</p>
     * <p>21A Customer Reference Number</p>
     * <p>In Sequence A, 22A Purpose of Message</p>
     */
    protected String generateLabel(String sequenceName, FieldInfo fieldInfo) {
        return ValidationError.generateLabel(generateSequenceLabel(sequenceName), fieldInfo.getTag(), fieldInfo.getLabel());
    }

    private String generateSequenceLabel(String sequenceName) {
        if (StringUtils.isBlank(sequenceName)) {
            return StringUtils.EMPTY;
        }
        return "In Sequence ".concat(sequenceName);
    }
}
