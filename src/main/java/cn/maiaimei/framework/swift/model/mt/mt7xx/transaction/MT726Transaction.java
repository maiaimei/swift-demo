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
public class MT726Transaction extends MT798Transaction {

    private MT726IndexMessage indexMessage;
    private List<MT726DetailMessage> detailMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT726IndexMessage extends MT798IndexMessage {
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
    public static class MT726DetailMessage extends MT798DetailMessage {
        @Tag("21A")
        private String customerReferenceNumber;

        @Tag("27")
        private String sequenceOfTotal;

        @Tag("20")
        private String transactionReferenceNumber;

        @Tag("21")
        private String relatedReferenceNumber;

        @Tag("22D")
        private String formOfUndertaking;

        @Tag("23")
        private String undertakingNumber;

        @Tag("52A")
        private String issuer4Field52A;

        @Tag("52D")
        private String issuer4Field52D;

        @Tag("23H")
        private String functionOfMessage;

        @Tag("45D")
        private String narrative;

        @Tag("23X")
        private String fileIdentification;
    }
}
