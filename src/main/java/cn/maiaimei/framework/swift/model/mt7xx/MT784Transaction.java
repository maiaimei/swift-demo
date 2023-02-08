package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.SwiftMTTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MT784Transaction extends BaseMT798Transaction {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784IndexMessage extends BaseMT798IndexMessage {
        @SwiftMTTag("27A")
        private String customerReferenceNumber;
        @SwiftMTTag("21A")
        private String customerBusinessReference;
        @SwiftMTTag("21T")
        private String textPurpose;
        @SwiftMTTag("21P")
        private String bankReferenceNumber;
        @SwiftMTTag("21S")
        private String bankBusinessReference;
        @SwiftMTTag("13E")
        private String messageCreationDateTime;
        @SwiftMTTag("23E")
        private String methodOfTransmission;
        @SwiftMTTag("22K")
        private String typeOfUndertaking;
        @SwiftMTTag("12H")
        private String wordingOfUndertaking;
        @SwiftMTTag("22B")
        private String specialTerms;
        @SwiftMTTag("12L")
        private String languageOfStandardWording;
        @SwiftMTTag("31S")
        private String approximateExpiryDate;
        @SwiftMTTag("53C")
        private String liabilityAccount;
        @SwiftMTTag("25A")
        private String chargesAccount;
        @SwiftMTTag("20E")
        private String reference;
        @SwiftMTTag("31R")
        private String referenceDate;
        @SwiftMTTag("71F")
        private String totalOrderContractAmount;
        @SwiftMTTag("37J")
        private String undertakingValueInPercent;
        @SwiftMTTag("49Z")
        private String specialAgreements;
        @SwiftMTTag("29A")
        private String customerContact;
        @SwiftMTTag("29D")
        private String beneficiaryContact;
        @SwiftMTTag("29F")
        private String localUndertakingBeneficiaryContact;
        @SwiftMTTag("72Z")
        private String corporateToBankInformation;
        @SwiftMTTag("23X")
        private String fileIdentification;
        @SwiftMTTag("29S")
        private String customerIdentifier;
        @SwiftMTTag("29P")
        private String processingBankIdentifier;
        @SwiftMTTag("29U")
        private String leadBankIdentifier;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailMessage extends BaseMT798DetailMessage {
        @SwiftMTTag("27A")
        private String customerReferenceNumber;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784ExtensionMessage extends BaseMT798ExtensionMessage {
        @SwiftMTTag("27A")
        private String customerReferenceNumber;
    }
}
