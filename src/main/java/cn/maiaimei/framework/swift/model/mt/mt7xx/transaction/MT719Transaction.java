package cn.maiaimei.framework.swift.model.mt.mt7xx.transaction;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response to a DraftUndertaking
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MT719Transaction extends MT798Transaction {

    private MT719IndexMessage indexMessage;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT719IndexMessage extends MT798IndexMessage {
        @Tag("21A")
        private String customerReferenceNumber;
        @Tag("21T")
        private String customerBusinessReference;
        @Tag("21P")
        private String bankReferenceNumber;
        @Tag("21S")
        private String bankBusinessReference;
        @Tag("13E")
        private String messageCreationDateTime;
        @Tag("12K")
        private String draftTextVersion;
        @Tag("25G")
        private String approvalForIssuance;
        @Tag("77J")
        private String commentsOnDraftUndertaking;
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
}
