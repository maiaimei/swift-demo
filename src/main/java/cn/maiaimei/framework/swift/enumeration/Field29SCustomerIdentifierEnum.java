package cn.maiaimei.framework.swift.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Field29SCustomerIdentifierEnum {
    BICC("BIC"),
    OTHR("Other");

    private String description;

    Field29SCustomerIdentifierEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getCodes() {
        return Arrays.stream(Field29SCustomerIdentifierEnum.values()).map(Enum::name).collect(Collectors.toList());
    }
}
