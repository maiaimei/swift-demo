package cn.maiaimei.framework.swift.converter.mt.mt7xx;

import cn.maiaimei.framework.swift.model.mt.mt7xx.*;
import cn.maiaimei.framework.swift.util.SwiftUtils;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionToMT798Converter implements Converter<MT798Transaction, MT798Message> {

    @Autowired
    private SwiftUtils swiftUtils;

    @Override
    public MT798Message convert(MT798Transaction transaction) {
        MT798IndexMessage indexMessage = transaction.getIndexMessage();
        List<? extends MT798DetailMessage> detailMessages = transaction.getDetailMessages();
        List<? extends MT798ExtensionMessage> extensionMessages = transaction.getExtensionMessages();

        MT798Message mt798Message = new MT798Message();
        if (indexMessage != null) {
            MT798 mt798 = new MT798();
            swiftUtils.convert(indexMessage, mt798);
            mt798Message.setIndexMessage(mt798);
        }

        if (!CollectionUtils.isEmpty(detailMessages)) {
            mt798Message.setDetailMessages(new ArrayList<>());
            for (MT798DetailMessage detailMessage : detailMessages) {
                if (detailMessage == null) {
                    continue;
                }
                MT798 mt798 = new MT798();
                swiftUtils.convert(detailMessage, mt798);
                mt798Message.getDetailMessages().add(mt798);
            }
        }

        if (!CollectionUtils.isEmpty(extensionMessages)) {
            mt798Message.setExtensionMessages(new ArrayList<>());
            for (MT798ExtensionMessage extensionMessage : extensionMessages) {
                if (extensionMessage == null) {
                    continue;
                }
                MT798 mt798 = new MT798();
                swiftUtils.convert(extensionMessage, mt798);
                mt798Message.getExtensionMessages().add(mt798);
            }
        }

        return mt798Message;
    }

}
