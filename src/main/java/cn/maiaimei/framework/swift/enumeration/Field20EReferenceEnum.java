package cn.maiaimei.framework.swift.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Field20EReferenceEnum {
    TEND("TENDER"),
    ORDR("ORDER"),
    CONT("CONTRACT"),
    OFFR("OFFER"),
    DELV("DELIVERY"),
    PINV("PROFORMA INVOICE"),
    PROJ("PROJECT");

    private String description;

    Field20EReferenceEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean matches(String value) {
        for (Field20EReferenceEnum item : Field20EReferenceEnum.values()) {
            if (item.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getCodes() {
        return Arrays.stream(Field20EReferenceEnum.values()).map(Enum::name).collect(Collectors.toList());
    }
}
