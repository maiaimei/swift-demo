package cn.maiaimei.framework.swift.model.mt.mt7xx;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MT762Transaction extends MT798Transaction {
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

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT762DetailMessage extends MT798DetailMessage {
        @Tag("21P")
        private String bankReferenceNumber;
        @Tag("22D")
        private String formOfUndertaking;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT762ExtensionMessage extends MT798ExtensionMessage {
        @Tag("21P")
        private String bankReferenceNumber;
    }
}
