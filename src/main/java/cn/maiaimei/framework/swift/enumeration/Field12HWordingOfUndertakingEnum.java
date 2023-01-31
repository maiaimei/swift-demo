package cn.maiaimei.framework.swift.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Field12HWordingOfUndertakingEnum {
    STND("STANDARD WORDING OF ISSUING/LOCAL BANK"),
    WDAP("WORDING DRAFTED BY APPLICANT"),
    WDBF("WORDING DRAFTED BY BENEFICIARY"),
    OTHR("must be specified in Narrative");

    private String description;

    Field12HWordingOfUndertakingEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getCodes() {
        return Arrays.stream(Field12HWordingOfUndertakingEnum.values()).map(Enum::name).collect(Collectors.toList());
    }
}
