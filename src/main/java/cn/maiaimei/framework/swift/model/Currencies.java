package cn.maiaimei.framework.swift.model;

import lombok.Data;

import java.util.List;

@Data
public class Currencies {
    private List<CurrencyInfo> currencies;

    @Data
    public static class CurrencyInfo {
        private String country;
        private String countryShortName;
        private String currency;
        private int fractionDigits;
    }
}
