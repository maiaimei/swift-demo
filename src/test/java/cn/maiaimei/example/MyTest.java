package cn.maiaimei.example;

import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field27A;
import com.prowidesoftware.swift.model.field.Field77;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

public class MyTest {
    String generateMT784() {
        MT798 mt798 = new MT798();
        mt798.addField(new Field12("784"));
        mt798.addField(new Field20("123456"));
        mt798.addField(new Field77());
        mt798.addField(new Field27A("1/2"));
        return mt798.message();
    }
}
