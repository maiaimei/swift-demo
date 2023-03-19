package cn.maiaimei.framework.swift.model.mt.mt7xx.transaction;

import cn.maiaimei.framework.swift.annotation.Tag;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798IndexMessage;
import cn.maiaimei.framework.swift.model.mt.mt7xx.MT798Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT778Transaction extends MT798Transaction {

    private MT778IndexMessage indexMessage;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT778IndexMessage extends MT798IndexMessage {
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

        @Tag("13E")
        private String messageCreationDateTime;

        @Tag("31C")
        private String dateOfExtendOrPayRequest;

        @Tag("34D")
        private String amountClaimed;

        @Tag("31L")
        private String newValidityExpiryDate;

        @Tag("12D")
        private String extendOrPayInstructions;

        @Tag("53C")
        private String settlementAccount;

        @Tag("25A")
        private String alternativeChargesAccount;

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
}
