package cn.maiaimei.framework.swift.validation.mt;

import cn.maiaimei.framework.swift.enumeration.*;
import cn.maiaimei.framework.swift.validation.FieldEnum;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MT784Validation extends AbstractMTValidation {
    @Override
    protected void doValidateFields(ValidationResult result, MT798 mt798, SwiftTagListBlock block) {

    }

    @Override
    public boolean supports(String subMessageType) {
        return "784".equals(subMessageType);
    }

    @Override
    public List<String> getMandatoryFields() {
        return Arrays.asList("21A", "21T", "25F", "13E", "22K", "12H");
    }

    @Override
    public List<FieldEnum> getEnums() {
        List<FieldEnum> fieldEnums = new ArrayList<>();
        fieldEnums.add(FieldEnum.builder().tag("25F").required(Boolean.TRUE).enumItems(Field25FTextPurposeEnum.getCodes()).build());
        fieldEnums.add(FieldEnum.builder().tag("23E").number(1).required(Boolean.TRUE).enumItems(Field23EMethodOfTransmissionEnum.getCodes()).build());
        fieldEnums.add(FieldEnum.builder().tag("22K").number(1).required(Boolean.TRUE).enumItems(Field22KTypeOfUndertakingEnum.getCodes()).build());
        fieldEnums.add(FieldEnum.builder().tag("12H").number(1).required(Boolean.TRUE).enumItems(Field12HWordingOfUndertakingEnum.getCodes()).build());
        // TODO: add Field22BSpecialTermsEnum
        //fieldEnums.add(FieldEnum.builder().tag("22B").required(Boolean.TRUE).enumItems(Field22B.getCodes()).build());
        fieldEnums.add(FieldEnum.builder().tag("20E").number(1).required(Boolean.TRUE).enumItems(Field20EReferenceEnum.getCodes()).build());
        fieldEnums.add(FieldEnum.builder().tag("23X").number(1).required(Boolean.TRUE).enumItems(Field23XFileIdentificationEnum.getCodes()).build());
        fieldEnums.add(FieldEnum.builder().tag("29S").number(1).required(Boolean.TRUE).enumItems(Field29SCustomerIdentifierEnum.getCodes()).build());
        return fieldEnums;
    }
}
