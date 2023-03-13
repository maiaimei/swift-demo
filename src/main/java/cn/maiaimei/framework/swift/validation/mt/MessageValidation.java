package cn.maiaimei.framework.swift.validation.mt;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.mt.AbstractMT;

public interface MessageValidation {
    void validate(ValidationResult result, AbstractMT mt);
}
