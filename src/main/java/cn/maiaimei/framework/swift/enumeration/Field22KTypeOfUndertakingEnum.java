package cn.maiaimei.framework.swift.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Field22KTypeOfUndertakingEnum {
    LEAS("Lease"),
    OTHR("any other undertaking type, which must be specified in narrative (2nd subfield)"),
    PAYM("Payment"),
    PERF("Performance"),
    RETN("Retention"),
    SHIP("Shipping"),
    TEND("Tender or Bid"),
    WARR("Warranty/Maintenance");
    
    private String description;

    Field22KTypeOfUndertakingEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean matches(String value) {
        for (Field22KTypeOfUndertakingEnum item : Field22KTypeOfUndertakingEnum.values()) {
            if (item.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getCodes() {
        return Arrays.stream(Field22KTypeOfUndertakingEnum.values()).map(Enum::name).collect(Collectors.toList());
    }
}
