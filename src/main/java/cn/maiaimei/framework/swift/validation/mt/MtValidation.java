package cn.maiaimei.framework.swift.validation.mt;

import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.mt.AbstractMT;

public interface MtValidation {
    void validate(ValidationResult result, AbstractMT mt);
}
