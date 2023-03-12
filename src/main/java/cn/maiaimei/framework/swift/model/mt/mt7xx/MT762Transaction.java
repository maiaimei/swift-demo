package cn.maiaimei.framework.swift.model.mt.mt7xx;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.mt.field.Field23X;
import cn.maiaimei.framework.swift.model.mt.field.Field29S;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Notification of Draft or Issue of Guarantee / Standby Letter of Credit / Undertaking
 */
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
        private Field23X fileIdentification;
        @Tag("29S")
        private Field29S customerIdentifier;
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
