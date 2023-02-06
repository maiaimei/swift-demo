package cn.maiaimei.framework.swift.validation;

import cn.maiaimei.framework.swift.validation.engine.MT798ValidationEngine;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class ValidationEngine {

    @Autowired(required = false)
    MT798ValidationEngine mt798ValidationEngine;

    public ValidationResult validate(MT798 mt798) {
        if (mt798ValidationEngine == null) {
            log.warn("Please adjust swift.validation.enabled or swift.validation.enabled-mt7xx to true in the configuration file");
            ValidationResult result = new ValidationResult();
            result.setErrorMessages(new ArrayList<>());
            return result;
        }
        return mt798ValidationEngine.validate(mt798);
    }
}
