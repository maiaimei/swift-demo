package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Component;
import lombok.Data;

@Data
public class OptionDPartyField {
    private String value;
    @Component(index = 1)
    private String dCMark;
    @Component(index = 2)
    private String account;
    @Component(index = 3)
    private String nameAndAddress;
    @Component(index = 4)
    private String nameAndAddress2;
    @Component(index = 5)
    private String nameAndAddress3;
    @Component(index = 6)
    private String nameAndAddress4;
}
