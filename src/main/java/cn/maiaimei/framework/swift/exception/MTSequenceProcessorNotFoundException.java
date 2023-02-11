package cn.maiaimei.framework.swift.exception;

public class MTSequenceProcessorNotFoundException extends RuntimeException {
    public MTSequenceProcessorNotFoundException() {
        super();
    }

    public MTSequenceProcessorNotFoundException(String messageType) {
        super("Can't found MTSequenceProcessor for MT" + messageType);
    }
}
