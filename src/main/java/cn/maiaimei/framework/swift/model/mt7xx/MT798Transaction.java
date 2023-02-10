package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.model.BaseTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MT798Transaction extends BaseTransaction {
    private MT798IndexMessage indexMessage;
    private List<? extends MT798DetailMessage> detailMessages;
    private List<? extends MT798ExtensionMessage> extensionMessages;
}
