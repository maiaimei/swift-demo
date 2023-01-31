package cn.maiaimei.framework.swift.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Field23EMethodOfTransmissionEnum {
    /**
     * BY TELECOMMUNICATION
     */
    TELE("BY TELECOMMUNICATION"),

    /**
     * BY COURIER
     */
    COUR("BY COURIER");

    private String description;

    Field23EMethodOfTransmissionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getCodes() {
        return Arrays.stream(Field23EMethodOfTransmissionEnum.values()).map(Enum::name).collect(Collectors.toList());
    }
}
