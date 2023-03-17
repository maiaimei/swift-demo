package cn.maiaimei.framework.swift.model.mt.mt7xx.transaction;

import cn.maiaimei.framework.swift.annotation.Sequence;
import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.annotation.WithSequence;
import cn.maiaimei.framework.swift.model.BaseSequence;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798ExtensionMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Notification of Draft or Issue of Guarantee / Standby Letter of Credit / Undertaking
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MT762Transaction extends MT798Transaction {

    private MT762IndexMessage indexMessage;
    private List<MT762DetailMessage> detailMessages;
    private List<MT762ExtensionMessage> extensionMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT762IndexMessage extends MT798IndexMessage {
        @Tag("21A")
        private String customerReferenceNumber;
        @Tag("21T")
        private String customerBusinessReference;
        @Tag("21P")
        private String bankReferenceNumber;
        @Tag("21S")
        private String bankBusinessReference;
        @Tag("20")
        private String undertakingNumber;
        @Tag("25F")
        private String textPurpose;
        @Tag("12K")
        private String draftTextVersion;
        @Tag("13E")
        private String messageCreationDateTime;
        @Tag("49Z")
        private String specialAgreements;
        @Tag("29B")
        private String bankContact;
        @Tag("72Z")
        private String corporateToBankInformation;
        @Tag("23X")
        private String fileIdentification;
        @Tag("29S")
        private String customerIdentifier;
        @Tag("29P")
        private String processingBankIdentifier;
        @Tag("29U")
        private String leadBankIdentifier;
    }

    @WithSequence
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT762DetailMessage extends MT798DetailMessage {
        @Tag("21P")
        private String bankReferenceNumber;
        @Sequence("A")
        private MT762DetailSequenceA sequenceA;
        @Sequence("B")
        private MT762DetailSequenceB sequenceB;
        @Sequence("C")
        private MT762DetailSequenceC sequenceC;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT762DetailSequenceA extends BaseSequence {
        @Tag("15A")
        private String field15A = "";
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
    public static class MT762DetailSequenceB extends BaseSequence {
        @Tag("15B")
        private String field15B = "";
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
        @Tag("52A")
        private String issuer4Field52A;
        @Tag("52D")
        private String issuer4Field52D;
        @Tag("59")
        private String beneficiary4Field59;
        @Tag("59A")
        private String beneficiary4Field59A;
        @Tag("56A")
        private String advisingBank4Field56A;
        @Tag("56D")
        private String advisingBank4Field56D;
        @Tag("23")
        private String advisingBankReference;
        @Tag("57A")
        private String adviseThroughBank4Field57A;
        @Tag("57D")
        private String adviseThroughBank4Field57D;
        @Tag("32B")
        private String undertakingAmount;
        @Tag("39D")
        private String additionalAmountInformation;
        @Tag("41F")
        private String availableWith4Field41F;
        @Tag("41G")
        private String availableWith4Field41G;
        @Tag("71D")
        private String charges;
        @Tag("45C")
        private String presentationInstructions;
        @Tag("77U")
        private String undertakingTermsAndConditions;
        @Tag("49")
        private String confirmationInstructions;
        @Tag("58A")
        private String requestedConfirmationParty4Field58A;
        @Tag("58D")
        private String requestedConfirmationParty4Field58D;
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
    public static class MT762DetailSequenceC extends BaseSequence {
        @Tag("15C")
        private String field15C = "";
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
        @Tag("52A")
        private String issuer4Field52A;
        @Tag("52D")
        private String issuer4Field52D;
        @Tag("59")
        private String beneficiary;
        @Tag("32B")
        private String undertakingAmount;
        @Tag("39D")
        private String additionalAmountInformation;
        @Tag("71D")
        private String charges;
        @Tag("41F")
        private String availableWith4Field41F;
        @Tag("41G")
        private String availableWith4Field41G;
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
    public static class MT762ExtensionMessage extends MT798ExtensionMessage {
        @Tag("21P")
        private String bankReferenceNumber;
        @Tag("27")
        private String sequenceOfTotal;
        @Tag("20")
        private String undertakingNumber;
        @Tag("52A")
        private String issuer4Field52A;
        @Tag("52D")
        private String issuer4Field52D;
        @Tag("77U")
        private String undertakingTermsAndConditions;
        @Tag("77L")
        private String requestedLocalUndertakingTermsAndConditions;
    }
}
