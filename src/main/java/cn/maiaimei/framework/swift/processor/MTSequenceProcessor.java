package cn.maiaimei.framework.swift.processor;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;

import java.util.List;
import java.util.Map;

public interface MTSequenceProcessor {
    boolean supportsMessageType(String subMessageType);

    Map<String, List<SwiftTagListBlock>> getSequenceMap(AbstractMT mt);
}
