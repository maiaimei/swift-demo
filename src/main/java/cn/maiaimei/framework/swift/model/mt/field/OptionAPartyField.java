package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Component;
import lombok.Data;

@Data
public class OptionAPartyField {
    private String value;
    @Component(index = 1)
    private String dCMark;
    @Component(index = 2)
    private String account;
    @Component(index = 3)
    private String identifierCode;
}
