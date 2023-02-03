package cn.maiaimei.framework.swift.validation;

import cn.maiaimei.framework.swift.validation.engine.MT798ValidationEngine;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationEngine {

    @Autowired
    MT798ValidationEngine mt798ValidationEngine;

    public ValidationResult validate(MT798 mt798) {
        return mt798ValidationEngine.validate(mt798);
    }
}
