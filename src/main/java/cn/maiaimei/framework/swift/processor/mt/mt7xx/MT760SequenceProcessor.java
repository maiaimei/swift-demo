package cn.maiaimei.framework.swift.processor.mt.mt7xx;

import cn.maiaimei.framework.swift.processor.MessageSequenceProcessor;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt7xx.MT760;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MT760SequenceProcessor implements MessageSequenceProcessor {
    @Override
    public boolean supportsMessageType(String subMessageType) {
        return "760".equals(subMessageType);
    }

    @Override
    public Map<String, List<SwiftTagListBlock>> getSequenceMap(AbstractMT mt) {
        MT760 mt760 = MT760.parse(mt.message());
        Map<String, List<SwiftTagListBlock>> map = new HashMap<>();
        map.put("A", Collections.singletonList(mt760.getSequenceA()));
        map.put("B", Collections.singletonList(mt760.getSequenceB()));
        map.put("C", Collections.singletonList(mt760.getSequenceC()));
        return map;
    }
}
