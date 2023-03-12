package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;

@Data
public class RequestedConfirmationParty {
    private boolean isField58A;
    private boolean isField58D;
    @Tag("58A")
    private OptionAPartyField field58A;
    @Tag("58D")
    private OptionDPartyField field58D;
}
