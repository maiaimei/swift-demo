package cn.maiaimei.framework.swift.validation.mt.mt7xx;

import cn.maiaimei.framework.swift.validation.FieldEnum;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.mt.AbstractMTValidation;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MT760Validation extends AbstractMTValidation {
    @Override
    protected void doValidateFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block) {

    }

    @Override
    public boolean supports(String subMessageType) {
        return "760".equals(subMessageType);
    }

    @Override
    public List<String> getMandatoryFields() {
        return null;
    }

    @Override
    public List<FieldEnum> getEnums() {
        return null;
    }
}
