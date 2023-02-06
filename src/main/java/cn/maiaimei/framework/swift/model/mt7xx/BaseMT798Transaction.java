package cn.maiaimei.framework.swift.model.mt7xx;

import cn.maiaimei.framework.swift.model.BaseTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseMT798Transaction extends BaseTransaction {
    private BaseMT798IndexMessage indexMessage;
    private List<BaseMT798DetailMessage> detailMessages;
    private List<BaseMT798ExtensionMessage> extensionMessages;
}
