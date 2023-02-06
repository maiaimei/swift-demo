package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.annotation.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class MT784Transaction extends BaseMT798Transaction {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784IndexMessage extends BaseMT798IndexMessage {
        @Tag("27A")
        private String customerReferenceNumber;
        @Tag("21A")
        private String customerBusinessReference;
        @Tag("21T")
        private String textPurpose;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784DetailMessage extends BaseMT798DetailMessage {
        @Tag("27A")
        private String customerReferenceNumber;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MT784ExtensionMessage extends BaseMT798ExtensionMessage {
        @Tag("27A")
        private String customerReferenceNumber;
    }
}
