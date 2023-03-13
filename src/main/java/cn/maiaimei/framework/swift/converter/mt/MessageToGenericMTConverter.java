package cn.maiaimei.framework.swift.converter.mt;

import cn.maiaimei.framework.swift.model.BaseMessage;
import cn.maiaimei.framework.swift.util.SwiftUtils;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToGenericMTConverter implements Converter<BaseMessage, AbstractMT> {

    @Autowired
    private SwiftUtils swiftUtils;

    @Override
    public AbstractMT convert(BaseMessage message) {
        AbstractMT mt = new AbstractMT() {
            @Override
            public String getMessageType() {
                return null;
            }
        };
        swiftUtils.convert(message, mt);
        return mt;
    }
}
