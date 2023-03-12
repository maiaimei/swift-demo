package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;

@Data
public class Issuer {
    @Tag("52A")
    private OptionAPartyField field52A;
    @Tag("52D")
    private OptionDPartyField field52D;

    public boolean isField52A() {
        return field52A != null;
    }

    public boolean isField52D() {
        return field52D != null;
    }
}
