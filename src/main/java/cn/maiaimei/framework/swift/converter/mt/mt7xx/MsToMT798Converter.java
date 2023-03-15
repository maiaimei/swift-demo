package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.converter.MsToMtConverter;
import cn.maiaimei.framework.swift.model.BaseMessage;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convert BaseMessage to MT798
 */
@Component
public class MsToMT798Converter implements Converter<BaseMessage, MT798> {

    private final MsToMtConverter msToMtConverter;

    public MsToMT798Converter(MsToMtConverter msToMtConverter) {
        this.msToMtConverter = msToMtConverter;
    }

    @Override
    public MT798 convert(BaseMessage ms) {
        final MT798 mt798 = new MT798();
        msToMtConverter.doConvert(ms, mt798);
        return mt798;
    }
}
