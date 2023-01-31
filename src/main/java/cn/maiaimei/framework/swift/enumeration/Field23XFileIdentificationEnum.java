package cn.maiaimei.framework.swift.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Field23XFileIdentificationEnum {
    FACT("SWIFTNet FileAct"),
    FAXT("Fax transfer"),
    EMAL("Email transfer"),
    MAIL("Postal delivery"),
    COUR("Courier delivery (e.g., FedEx, DHL, UPS)"),
    HOST("Host-to-Host (Proprietary bank channel)"),
    OTHR("Other delivery channel");

    private String description;

    Field23XFileIdentificationEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getCodes() {
        return Arrays.stream(Field23XFileIdentificationEnum.values()).map(Enum::name).collect(Collectors.toList());
    }
}
