package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;

@Data
public class AdviseThroughBank {
    private boolean isField57A;
    private boolean isField57D;
    @Tag("57A")
    private OptionAPartyField field57A;
    @Tag("57D")
    private OptionDPartyField field57D;
}
