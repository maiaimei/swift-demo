package cn.maiaimei.framework.swift.validation.mt;

import cn.maiaimei.framework.swift.validation.FieldEnum;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GeneralMTValidation extends AbstractMTValidation {
    @Override
    public boolean supports(String subMessageType) {
        return false;
    }

    @Override
    public List<String> getMandatoryFields() {
        return Collections.emptyList();
    }

    @Override
    public List<FieldEnum> getEnums() {
        return Collections.emptyList();
    }

    @Override
    protected void doValidateFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block) {

    }
}
