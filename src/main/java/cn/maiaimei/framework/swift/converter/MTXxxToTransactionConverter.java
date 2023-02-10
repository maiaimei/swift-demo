package cn.maiaimei.framework.swift.converter;

import cn.maiaimei.framework.swift.model.BaseTransaction;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MTXxxToTransactionConverter implements Converter<AbstractMT, BaseTransaction> {

    @Override
    public BaseTransaction convert(AbstractMT mt) {
        return null;
    }
    
}
