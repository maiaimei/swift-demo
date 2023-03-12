package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Component;
import lombok.Data;

@Data
public class Field31R {
    private String value;
    @Component(index = 1)
    private String date1;
    @Component(index = 2)
    private String date2;
}
