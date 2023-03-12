package cn.maiaimei.framework.swift.model;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyList {
    private List<CurrencyInfo> items;

    @Data
    public static class CurrencyInfo {
        private String country;
        private String countryShortName;
        private String currency;
        private int fractionDigits;
    }
}
