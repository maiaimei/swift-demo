package cn.maiaimei.framework.swift.processor;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DefaultMTSequenceProcessor implements MTSequenceProcessor {
    @Override
    public boolean supportsMessageType(String subMessageType) {
        return false;
    }

    @Override
    public Map<String, List<SwiftTagListBlock>> getSequenceMap(AbstractMT mt) {
        return Collections.emptyMap();
    }
}
