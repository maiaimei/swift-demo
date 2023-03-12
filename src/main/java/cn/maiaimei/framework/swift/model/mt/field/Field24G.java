package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Component;
import lombok.Data;

@Data
public class Field24G {
    private String value;
    @Component(index = 1)
    private String code;
    @Component(index = 2)
    private String nameAndAddress;
    @Component(index = 3)
    private String nameAndAddress2;
    @Component(index = 4)
    private String nameAndAddress3;
    @Component(index = 5)
    private String nameAndAddress4;
    @Component(index = 6)
    private String nameAndAddress5;
    @Component(index = 7)
    private String nameAndAddress6;
}
