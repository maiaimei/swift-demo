package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;

@Data
public class AdvisingBank {
    private boolean isField56A;
    private boolean isField56D;
    @Tag("56A")
    private OptionAPartyField field56A;
    @Tag("56D")
    private OptionDPartyField field56D;
}
