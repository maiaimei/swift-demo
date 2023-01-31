package cn.maiaimei.framework.swift.validation.mt;

import cn.maiaimei.framework.swift.validation.FieldEnum;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

import java.util.List;

public interface MTValidation {
    boolean supports(String subMessageType);

    void validateFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block);

    List<String> getMandatoryFields();

    List<FieldEnum> getEnums();
}
