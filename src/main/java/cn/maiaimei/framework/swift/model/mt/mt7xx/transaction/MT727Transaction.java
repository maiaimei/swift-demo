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
public class MT727Transaction extends MT798Transaction {

    private MT727IndexMessage indexMessage;
    private List<MT727DetailMessage> detailMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT727IndexMessage extends MT798IndexMessage {
        @Tag("21T")
        private String customerBusinessReference;

        @Tag("21P")
        private String bankReferenceNumber;

        @Tag("21S")
        private String bankBusinessReference;

        @Tag("13E")
        private String messageCreationDateTime;

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
    public static class MT727DetailMessage extends MT798DetailMessage {
        @Tag("21P")
        private String bankReferenceNumber;

        @Tag("20")
        private String undertakingNumber;

        @Tag("21")
        private String relatedReference;

        @Tag("52A")
        private String issuer4Field52A;

        @Tag("52D")
        private String issuer4Field52D;

        @Tag("31C")
        private String dateOfIssue;

        @Tag("59")
        private String beneficiary4Field59;

        @Tag("59A")
        private String beneficiary4Field59A;

        @Tag("56A")
        private String advisingBank4Field56A;

        @Tag("56D")
        private String advisingBank4Field56D;

        @Tag("57A")
        private String adviseThroughBank4Field57A;

        @Tag("57D")
        private String adviseThroughBank4Field57D;

        @Tag("31E")
        private String finalDateOfExpiry;

        @Tag("72Z")
        private String senderToReceiverInformation;

        @Tag("23X")
        private String fileIdentification;
    }
}
