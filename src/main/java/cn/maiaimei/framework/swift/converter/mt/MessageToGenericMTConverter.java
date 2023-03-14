package cn.maiaimei.framework.swift.converter.mt;

import cn.maiaimei.framework.swift.converter.MsToMtConverter;
import cn.maiaimei.framework.swift.model.BaseMessage;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO: MessageToGenericMTConverter
@Component
public class MessageToGenericMTConverter implements Converter<BaseMessage, AbstractMT> {

    @Autowired
    private MsToMtConverter msToMtConverter;

    @Override
    public AbstractMT convert(BaseMessage message) {
        AbstractMT mt = new AbstractMT() {
            @Override
            public String getMessageType() {
                return null;
            }
        };
        msToMtConverter.convert(message, mt);
        return mt;
    }
}
