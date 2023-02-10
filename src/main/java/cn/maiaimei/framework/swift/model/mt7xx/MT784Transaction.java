package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.SwiftTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MT784Transaction extends MT798Transaction {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784IndexMessage extends MT798IndexMessage {
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
        @SwiftTag(value = "13E", index = 1)
        private String messageCreationDate;
        @SwiftTag(value = "13E", index = 2)
        private String messageCreationTime;
        @SwiftTag("23E")
        private String methodOfTransmission;
        @SwiftTag(value = "23E", index = 1)
        private String methodOfTransmissionMethod;
        @SwiftTag(value = "23E", index = 2)
        private String methodOfTransmissionAdditionalInformation;
        @SwiftTag("22K")
        private String typeOfUndertaking;
        @SwiftTag(value = "22K", index = 1)
        private String typeOfUndertakingCode;
        @SwiftTag(value = "22K", index = 2)
        private String typeOfUndertakingNarrative;
        @SwiftTag("12H")
        private String wordingOfUndertaking;
        @SwiftTag("22B")
        private String specialTerms;
        @SwiftTag("12L")
        private String languageOfStandardWording;
        @SwiftTag(value = "12L", index = 1)
        private String languageOfStandardWordingCode;
        @SwiftTag(value = "12L", index = 2)
        private String languageOfStandardWordingNarrative;
        @SwiftTag("31S")
        private String approximateExpiryDate;
        @SwiftTag("53C")
        private String liabilityAccount;
        @SwiftTag("25A")
        private String chargesAccount;
        @SwiftTag("20E")
        private String reference;
        @SwiftTag(value = "20E", index = 1)
        private String referenceCode;
        @SwiftTag(value = "20E", index = 2)
        private String referenceReference;
        @SwiftTag("31R")
        private String referenceDate;
        @SwiftTag(value = "31R", index = 1)
        private String referenceDateDate1;
        @SwiftTag(value = "31R", index = 2)
        private String referenceDateDate2;
        @SwiftTag("71F")
        private String totalOrderContractAmount;
        @SwiftTag(value = "71F", index = 1)
        private String totalOrderContractAmountCurrency;
        @SwiftTag(value = "71F", index = 2)
        private String totalOrderContractAmountAmount;
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
        @SwiftTag(value = "23X", index = 1)
        private String fileIdentificationCode;
        @SwiftTag(value = "23X", index = 2)
        private String fileIdentificationFileNameOrReference;
        @SwiftTag("29S")
        private String customerIdentifier;
        @SwiftTag(value = "29S", index = 1)
        private String customerIdentifierCode;
        @SwiftTag(value = "29S", index = 2)
        private String customerIdentifierPartyIdentifier;
        @SwiftTag("29P")
        private String processingBankIdentifier;
        @SwiftTag("29U")
        private String leadBankIdentifier;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailMessage extends MT798DetailMessage {
        @SwiftTag("27A")
        private String customerReferenceNumber;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784ExtensionMessage extends MT798ExtensionMessage {
        @SwiftTag("27A")
        private String customerReferenceNumber;
    }
}
