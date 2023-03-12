package cn.maiaimei.framework.swift.model.mt.mt7xx;

import cn.maiaimei.framework.swift.model.BaseTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798Transaction extends BaseTransaction {
    private MT798IndexMessage indexMessage;
    private List<? extends MT798DetailMessage> detailMessages;
    private List<? extends MT798ExtensionMessage> extensionMessages;

    @SneakyThrows
    public <S, T> List<T> convert(List<S> sourceMessages, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourceMessages)) {
            return null;
        }
        List<T> list = new ArrayList<>(sourceMessages.size());
        for (S sourceMessage : sourceMessages) {
            list.add((T) sourceMessage);
        }
        return list;
    }
}
