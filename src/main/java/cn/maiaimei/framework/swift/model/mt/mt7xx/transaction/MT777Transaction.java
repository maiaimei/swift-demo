package cn.maiaimei.framework.swift.model.mt.mt7xx.transaction;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798DetailMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT777Transaction extends MT798Transaction {

    private MT777IndexMessage indexMessage;
    private List<MT777DetailMessage> detailMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT777IndexMessage extends MT798IndexMessage {
        @Tag("21T")
        private String customerBusinessReference;

        @Tag("21P")
        private String bankReferenceNumber;

        @Tag("21S")
        private String bankBusinessReference;

        @Tag("20")
        private String undertakingNumber;

        @Tag("13E")
        private String messageCreationDateTime;

        @Tag("31C")
        private String dateOfExtendOrPayRequest;

        @Tag("34D")
        private String amountClaimed;

        @Tag("31L")
        private String newValidityExpiryDate;

        @Tag("49J")
        private String textOfExtendOrPayRequest;

        @Tag("78B")
        private String instructionsFromTheBank;

        @Tag("31T")
        private String latestDateForReply;

        @Tag("29B")
        private String bankContact;

        @Tag("72Z")
        private String bankToCorporateInformation;

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
    public static class MT777DetailMessage extends MT798DetailMessage {
        @Tag("21P")
        private String bankReferenceNumber;
    }
}
