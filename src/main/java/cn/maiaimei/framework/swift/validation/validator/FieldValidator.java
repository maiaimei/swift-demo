package cn.maiaimei.framework.swift.validation.validator;

import cn.maiaimei.framework.swift.validation.config.FieldComponentInfo;
import com.prowidesoftware.swift.model.field.Field;

public interface FieldValidator {
    String validate(FieldComponentInfo fieldComponentInfo, Field field, String label, String value);
}
