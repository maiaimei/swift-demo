package cn.maiaimei.framework.swift.model.mt.mt7xx;

import cn.maiaimei.framework.swift.model.BaseTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class MT798Transaction extends BaseTransaction {
    private MT798IndexMessage indexMessage;
    private List<MT798DetailMessage> detailMessages;
    private List<MT798ExtensionMessage> extensionMessages;

    public <T> T convertToConcreteIndexMessage(Class<T> clazz) {
        if (log.isDebugEnabled()) {
            log.debug("concrete indexMessage class is: {}", clazz.getName());
        }
        return (T) indexMessage;
    }

    public <T> List<T> convertToConcreteDetailMessages(Class<T> clazz) {
        if (log.isDebugEnabled()) {
            log.debug("concrete detailMessage class is: {}", clazz.getName());
        }
        if (CollectionUtils.isEmpty(detailMessages)) {
            return null;
        }
        List<T> list = new ArrayList<>(detailMessages.size());
        for (MT798DetailMessage detailMessage : detailMessages) {
            list.add((T) detailMessage);
        }
        return list;
    }

    public <T> List<T> convertToConcreteExtensionMessages(Class<T> clazz) {
        if (log.isDebugEnabled()) {
            log.debug("concrete extensionMessage class is: {}", clazz.getName());
        }
        if (CollectionUtils.isEmpty(extensionMessages)) {
            return null;
        }
        List<T> list = new ArrayList<>(extensionMessages.size());
        for (MT798ExtensionMessage extensionMessage : extensionMessages) {
            list.add((T) extensionMessage);
        }
        return list;
    }

}
