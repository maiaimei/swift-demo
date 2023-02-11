package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.SequenceMessage;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.BaseSequence;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MT784Transaction extends MT798Transaction {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784IndexMessage extends MT798IndexMessage {
        @Tag("27A")
        private String customerReferenceNumber;
        @Tag("21A")
        private String customerBusinessReference;
        @Tag("21T")
        private String textPurpose;
        @Tag("21P")
        private String bankReferenceNumber;
        @Tag("21S")
        private String bankBusinessReference;
        @Tag("13E")
        private String messageCreationDateTime;
        @Tag(value = "13E", index = 1)
        private String messageCreationDate;
        @Tag(value = "13E", index = 2)
        private String messageCreationTime;
        @Tag("23E")
        private String methodOfTransmission;
        @Tag(value = "23E", index = 1)
        private String methodOfTransmissionMethod;
        @Tag(value = "23E", index = 2)
        private String methodOfTransmissionAdditionalInformation;
        @Tag("22K")
        private String typeOfUndertaking;
        @Tag(value = "22K", index = 1)
        private String typeOfUndertakingCode;
        @Tag(value = "22K", index = 2)
        private String typeOfUndertakingNarrative;
        @Tag("12H")
        private String wordingOfUndertaking;
        @Tag("22B")
        private String specialTerms;
        @Tag("12L")
        private String languageOfStandardWording;
        @Tag(value = "12L", index = 1)
        private String languageOfStandardWordingCode;
        @Tag(value = "12L", index = 2)
        private String languageOfStandardWordingNarrative;
        @Tag("31S")
        private String approximateExpiryDate;
        @Tag("53C")
        private String liabilityAccount;
        @Tag("25A")
        private String chargesAccount;
        @Tag("20E")
        private String reference;
        @Tag(value = "20E", index = 1)
        private String referenceCode;
        @Tag(value = "20E", index = 2)
        private String referenceReference;
        @Tag("31R")
        private String referenceDate;
        @Tag(value = "31R", index = 1)
        private String referenceDateDate1;
        @Tag(value = "31R", index = 2)
        private String referenceDateDate2;
        @Tag("71F")
        private String totalOrderContractAmount;
        @Tag(value = "71F", index = 1)
        private String totalOrderContractAmountCurrency;
        @Tag(value = "71F", index = 2)
        private String totalOrderContractAmountAmount;
        @Tag("37J")
        private String undertakingValueInPercent;
        @Tag("49Z")
        private String specialAgreements;
        @Tag("29A")
        private String customerContact;
        @Tag("29D")
        private String beneficiaryContact;
        @Tag("29F")
        private String localUndertakingBeneficiaryContact;
        @Tag("72Z")
        private String corporateToBankInformation;
        @Tag("23X")
        private String fileIdentification;
        @Tag(value = "23X", index = 1)
        private String fileIdentificationCode;
        @Tag(value = "23X", index = 2)
        private String fileIdentificationFileNameOrReference;
        @Tag("29S")
        private String customerIdentifier;
        @Tag(value = "29S", index = 1)
        private String customerIdentifierCode;
        @Tag(value = "29S", index = 2)
        private String customerIdentifierPartyIdentifier;
        @Tag("29P")
        private String processingBankIdentifier;
        @Tag("29U")
        private String leadBankIdentifier;
    }

    @SequenceMessage
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailMessage extends MT798DetailMessage {
        @Tag("27A")
        private String customerReferenceNumber;
        @Sequence("A")
        private MT784DetailSequenceA sequenceA;
        @Sequence("B")
        private MT784DetailSequenceB sequenceB;
        @Sequence("C")
        private MT784DetailSequenceC sequenceC;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailSequenceA extends BaseSequence {
        @Tag("27")
        private String sequenceOfTotal;
        @Tag("22A")
        private String purposeOfMessage;
        @Tag("72Z")
        private String senderToReceiverInformation;
        @Tag("23X")
        private String fileIdentification;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailSequenceB extends BaseSequence {
        @Tag("20")
        private String undertakingNumber;
        @Tag("30")
        private String dateOfIssue;
        @Tag("22D")
        private String formOfUndertaking;
        @Tag("40C")
        private String applicableRules;
        @Tag("23B")
        private String expiryType;
        @Tag("31E")
        private String dateOfExpiry;
        @Tag("35G")
        private String expiryConditionEvent;
        @Tag("50")
        private String applicant;
        @Tag("51")
        private String obligorInstructingParty;
        @Tag(value = "52a", tags = {"52A", "52D"})
        private String issuer;
        @Tag(value = "59a", tags = {"59", "59A"})
        private String beneficiary;
        @Tag(value = "56a", tags = {"56A", "56D"})
        private String advisingBank;
        @Tag("23")
        private String advisingBankReference;
        @Tag(value = "57a", tags = {"57A", "57D"})
        private String adviseThroughBank;
        @Tag("32B")
        private String undertakingAmount;
        @Tag("39D")
        private String additionalAmountInformation;
        @Tag(value = "41a", tags = {"41F", "41G"})
        private String availableWith;
        @Tag("71D")
        private String charges;
        @Tag("45C")
        private String presentationInstructions;
        @Tag("77U")
        private String undertakingTermsAndConditions;
        @Tag("49")
        private String confirmationInstructions;
        @Tag(value = "58a", tags = {"58A", "58D"})
        private String requestedConfirmationParty;
        @Tag("44H")
        private String governingLawAndOrPlaceOfJurisdiction;
        @Tag("23F")
        private String automaticExtensionPeriod;
        @Tag("78")
        private String automaticExtensionNonExtensionNotification;
        @Tag("26E")
        private String automaticExtensionNotificationPeriod;
        @Tag("31S")
        private String automaticExtensionFinalExpiryDate;
        @Tag("48B")
        private String demandIndicator;
        @Tag("48D")
        private String transferIndicator;
        @Tag("39E")
        private String transferConditions;
        @Tag("45L")
        private String underlyingTransactionDetails;
        @Tag("24E")
        private String deliveryOfOriginalUndertaking;
        @Tag("24G")
        private String deliveryToCollectionBy;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailSequenceC extends BaseSequence {
        @Tag("31C")
        private String requestedDateOfIssue;
        @Tag("22D")
        private String formOfUndertaking;
        @Tag("40C")
        private String applicableRules;
        @Tag("22K")
        private String typeOfUndertaking;
        @Tag("23B")
        private String expiryType;
        @Tag("31E")
        private String dateOfExpiry;
        @Tag("35G")
        private String expiryConditionEvent;
        @Tag("50")
        private String applicant;
        @Tag("51")
        private String obligorInstructingParty;
        @Tag(value = "52a", tags = {"52A", "52D"})
        private String issuer;
        @Tag("59")
        private String beneficiary;
        @Tag("32B")
        private String undertakingAmount;
        @Tag("39D")
        private String additionalAmountInformation;
        @Tag("71D")
        private String charges;
        @Tag(value = "41a", tags = {"41F", "41G"})
        private String availableWith;
        @Tag("45C")
        private String presentationInstructions;
        @Tag("77L")
        private String requestedLocalUndertakingTermsAndConditions;
        @Tag("22Y")
        private String standardWordingRequired;
        @Tag("40D")
        private String standardWordingRequestedLanguage;
        @Tag("44H")
        private String governingLawAndOrPlaceOfJurisdiction;
        @Tag("23F")
        private String automaticExtensionPeriod;
        @Tag("78")
        private String automaticExtensionNonExtensionNotification;
        @Tag("26E")
        private String automaticExtensionNotificationPeriod;
        @Tag("31S")
        private String automaticExtensionFinalExpiryDate;
        @Tag("48B")
        private String demandIndicator;
        @Tag("48D")
        private String transferIndicator;
        @Tag("39E")
        private String transferConditions;
        @Tag("45L")
        private String underlyingTransactionDetails;
        @Tag("24E")
        private String deliveryOfOriginalUndertaking;
        @Tag("24G")
        private String deliveryToCollectionBy;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784ExtensionMessage extends MT798ExtensionMessage {
        @Tag("21A")
        private String customerReferenceNumber;
        @Tag("27")
        private String sequenceOfTotal;
        @Tag("20")
        private String undertakingNumber;
        @Tag(value = "52a", tags = {"52A", "52D"})
        private String issuer;
        @Tag("77U")
        private String undertakingTermsAndConditions;
        @Tag("77L")
        private String requestedLocalUndertakingTermsAndConditions;
    }
}
