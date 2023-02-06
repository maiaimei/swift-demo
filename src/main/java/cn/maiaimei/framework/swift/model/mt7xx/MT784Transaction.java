package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.SwiftTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MT784Transaction extends BaseMT798Transaction {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784IndexMessage extends BaseMT798IndexMessage {
        @SwiftTag("27A")
        private String customerReferenceNumber;
        @SwiftTag("21A")
        private String customerBusinessReference;
        @SwiftTag("21T")
        private String textPurpose;
        @SwiftTag("21P")
        private String bankReferenceNumber;
        @SwiftTag("21S")
        private String bankBusinessReference;
        @SwiftTag("13E")
        private String messageCreationDateTime;
        @SwiftTag("23E")
        private String methodOfTransmission;
        @SwiftTag("22K")
        private String typeOfUndertaking;
        @SwiftTag("12H")
        private String wordingOfUndertaking;
        @SwiftTag("22B")
        private String specialTerms;
        @SwiftTag("12L")
        private String languageOfStandardWording;
        @SwiftTag("31S")
        private String approximateExpiryDate;
        @SwiftTag("53C")
        private String liabilityAccount;
        @SwiftTag("25A")
        private String chargesAccount;
        @SwiftTag("20E")
        private String reference;
        @SwiftTag("31R")
        private String referenceDate;
        @SwiftTag("71F")
        private String totalOrderContractAmount;
        @SwiftTag("37J")
        private String undertakingValueInPercent;
        @SwiftTag("49Z")
        private String specialAgreements;
        @SwiftTag("29A")
        private String customerContact;
        @SwiftTag("29D")
        private String beneficiaryContact;
        @SwiftTag("29F")
        private String localUndertakingBeneficiaryContact;
        @SwiftTag("72Z")
        private String corporateToBankInformation;
        @SwiftTag("23X")
        private String fileIdentification;
        @SwiftTag("29S")
        private String customerIdentifier;
        @SwiftTag("29P")
        private String processingBankIdentifier;
        @SwiftTag("29U")
        private String leadBankIdentifier;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailMessage extends BaseMT798DetailMessage {
        @SwiftTag("27A")
        private String customerReferenceNumber;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784ExtensionMessage extends BaseMT798ExtensionMessage {
        @SwiftTag("27A")
        private String customerReferenceNumber;
    }
}
