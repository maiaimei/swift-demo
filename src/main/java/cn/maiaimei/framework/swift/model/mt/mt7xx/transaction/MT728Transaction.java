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
public class MT728Transaction extends MT798Transaction {

    private MT728IndexMessage indexMessage;
    private List<MT728DetailMessage> detailMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT728IndexMessage extends MT798IndexMessage {
        @Tag("21A")
        private String customerReferenceNumber;

        @Tag("21T")
        private String customerBusinessReference;

        @Tag("20")
        private String undertakingNumber;

        @Tag("21P")
        private String bankReferenceNumber;

        @Tag("21S")
        private String bankBusinessReference;

        @Tag("30")
        private String dateOfAmendment;

        @Tag("26E")
        private String numberOfAmendment;

        @Tag("13E")
        private String messageCreationDateTime;

        @Tag("31C")
        private String dateOfIssue;

        @Tag("29A")
        private String customerContact;

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
    public static class MT728DetailMessage extends MT798DetailMessage {
        @Tag("21A")
        private String customerReferenceNumber;

        @Tag("20")
        private String transactionReferenceNumber;

        @Tag("21")
        private String relatedReferenceNumber;

        @Tag("52A")
        private String issuer5Field52A;

        @Tag("52D")
        private String issuer4Field52D;

        @Tag("26E")
        private String numberOfAmendment;

        @Tag("23R")
        private String amendmentStatus;

        @Tag("72Z")
        private String senderToReceiverInformation;

        @Tag("23X")
        private String fileIdentification;
    }
}
