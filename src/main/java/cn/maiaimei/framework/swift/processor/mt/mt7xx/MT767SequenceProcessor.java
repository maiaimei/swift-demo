package cn.maiaimei.framework.swift.processor.mt.mt7xx;

import cn.maiaimei.framework.swift.processor.MessageSequenceProcessor;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt7xx.MT767;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MT767SequenceProcessor implements MessageSequenceProcessor {
    @Override
    public boolean supportsMessageType(String messageType) {
        return "767".equals(messageType);
    }

    @Override
    public Map<String, List<SwiftTagListBlock>> getSequenceMap(AbstractMT mt) {
        MT767 mt767 = MT767.parse(mt.message());
        Map<String, List<SwiftTagListBlock>> map = new HashMap<>();
        map.put("A", Collections.singletonList(mt767.getSequenceA()));
        map.put("B", Collections.singletonList(mt767.getSequenceB()));
        map.put("C", Collections.singletonList(mt767.getSequenceC()));
        return map;
    }
}
