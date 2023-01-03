package cn.maiaimei.example.converter;

import cn.maiaimei.example.model.MT7xxModel;
import org.springframework.stereotype.Component;

@Component
public class MT784ModelConverterStrategy extends MT7xxModelConverterStrategy {
    @Override
    public MT7xxModel createMT7xxModel() {
        return null;
    }
}
