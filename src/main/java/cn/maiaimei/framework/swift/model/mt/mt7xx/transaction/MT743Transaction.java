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

@Data
@EqualsAndHashCode(callSuper = true)
public class MT743Transaction extends MT798Transaction {

    private MT743IndexMessage indexMessage;
    private List<MT743DetailMessage> detailMessages;
    private List<MT743ExtensionMessage> extensionMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT743IndexMessage extends MT798IndexMessage {
        @Tag("21P")
        private String bankReferenceNumber;

        @Tag("21S")
        private String bankBusinessReference;

        @Tag("20")
        private String undertakingNumber;

        @Tag("21A")
        private String customerReferenceNumber;

        @Tag("21T")
        private String customerBusinessReference;

        @Tag("31C")
        private String dateOfIssue;

        @Tag("13E")
        private String messageCreationDateTime;

        @Tag("29B")
        private String issuingGuarantorBankContact;

        @Tag("29D")
        private String advisingBankContact;

        @Tag("78B")
        private String instructionsFromTheBank;

        @Tag("72Z")
        private String bankToCorporateInformation;

        @Tag("23X")
        private String fileIdentification;

        @Tag("26E")
        private String numberOfAmendment;

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
    public static class MT743DetailMessage extends MT798DetailMessage {
        @Tag("21P")
        private String bankReferenceNumber;

        @Sequence("A")
        private MT743DetailSequenceA sequenceA;

        @Sequence("B")
        private MT743DetailSequenceB sequenceB;

        @Sequence("C")
        private MT743DetailSequenceC sequenceC;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT743DetailSequenceA extends BaseSequence {
        @Tag("15A")
        private String newSequence;

        @Tag("27")
        private String sequenceOfTotal;

        @Tag("21")
        private String relatedReference;

        @Tag("22A")
        private String purposeOfMessage;

        @Tag("23S")
        private String cancellationRequest;

        @Tag("72Z")
        private String senderToReceiverInformation;

        @Tag("23X")
        private String fileIdentification;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT743DetailSequenceB extends BaseSequence {
        @Tag("15B")
        private String newSequence;

        @Tag("20")
        private String undertakingNumber;

        @Tag("26E")
        private String numberOfAmendment;

        @Tag("30")
        private String dateOfAmendment;

        @Tag("52A")
        private String issuer4Field52A;

        @Tag("52D")
        private String issuer4Field52D;

        @Tag("32B")
        private String increaseOfUndertakingAmount;

        @Tag("33B")
        private String decreaseOfUndertakingAmount;

        @Tag("23B")
        private String expiryType;

        @Tag("31E")
        private String dateOfExpiry;

        @Tag("35G")
        private String expiryConditionEvent;

        @Tag("59")
        private String beneficiary4Field59;

        @Tag("59A")
        private String beneficiary4Field59A;

        @Tag("77U")
        private String otherAmendmentsOfUndertaking;

        @Tag("24E")
        private String deliveryOfAmendmentToUndertaking;

        @Tag("24G")
        private String deliveryToCollectionBy;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT743DetailSequenceC extends BaseSequence {
        @Tag("15C")
        private String newSequence;

        @Tag("32B")
        private String increaseOfLocalUndertakingAmount;

        @Tag("33B")
        private String decreaseOfLocalUndertakingAmount;

        @Tag("23B")
        private String expiryType;

        @Tag("31E")
        private String dateOfExpiry;

        @Tag("35G")
        private String expiryConditionEvent;

        @Tag("59")
        private String beneficiary;

        @Tag("77L")
        private String otherAmendmentsOfRequestedLocalUndertaking;

        @Tag("24E")
        private String deliveryOfOriginalUndertaking;

        @Tag("24G")
        private String deliveryToCollectionBy;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT743ExtensionMessage extends MT798ExtensionMessage {}
}
