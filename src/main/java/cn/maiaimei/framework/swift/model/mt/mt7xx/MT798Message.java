package cn.maiaimei.framework.swift.model.mt.mt7xx;

import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.Data;

import java.util.List;

@Data
public class MT798Message {
    private MT798 indexMessage;
    private List<MT798> detailMessages;
    private List<MT798> extensionMessages;
}
