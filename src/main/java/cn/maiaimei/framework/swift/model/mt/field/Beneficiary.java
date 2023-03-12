package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;

@Data
public class Beneficiary {
    private boolean isField59;
    private boolean isField59A;
    @Tag("59")
    private Field59 field59;
    @Tag("59A")
    private Field59A field59A;
}
