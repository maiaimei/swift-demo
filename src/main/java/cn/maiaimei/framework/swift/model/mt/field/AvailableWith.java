package cn.maiaimei.framework.swift.model.mt.field;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;

@Data
public class AvailableWith {
    private boolean isField41F;
    private boolean isField41G;
    @Tag("41F")
    private String identifierCode;
    @Tag("41G")
    private Field41G nameAndAddress;
}
