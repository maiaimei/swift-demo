package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Component;
import lombok.Data;

@Data
public class Field29S {
    private String customerIdentifier;
    @Component(index = 1)
    private String code;
    @Component(index = 2)
    private String partyIdentifier;
}
