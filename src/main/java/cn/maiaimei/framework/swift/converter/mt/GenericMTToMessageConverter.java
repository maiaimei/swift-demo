package cn.maiaimei.framework.swift.converter.mt;

import cn.maiaimei.framework.swift.model.BaseMessage;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenericMTToMessageConverter implements Converter<AbstractMT, BaseMessage> {

    @Override
    public BaseMessage convert(AbstractMT mt) {
        return null;
    }

}
