package cn.maiaimei.example.model;

import cn.maiaimei.example.annotation.SwiftMTTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public class MT784Model extends MT7xxModel {
    private IndexMessage indexMessage;
    private DetailMessage detailMessage;
    private List<ExtensionMessage> extensionMessages;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class IndexMessage extends MT7xxMessage {
        @SwiftMTTag("21A")
        private String customerReferenceNumber;
        @SwiftMTTag("21T")
        private String customerBusinessReference;
        @SwiftMTTag("25F")
        private String textPurpose;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DetailMessage extends MT7xxMessage {
        @SwiftMTTag("21A")
        private String customerReferenceNumber;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ExtensionMessage extends MT7xxMessage {
        @SwiftMTTag("21A")
        private String customerReferenceNumber;
    }
}
