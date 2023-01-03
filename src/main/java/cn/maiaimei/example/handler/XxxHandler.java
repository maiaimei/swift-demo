package cn.maiaimei.example.handler;

import cn.maiaimei.example.annotation.SwiftHandler;
import cn.maiaimei.example.converter.XxxConverter;
import org.springframework.beans.factory.annotation.Autowired;

@SwiftHandler
public class XxxHandler {
    private XxxConverter xxxConverter;

    @Autowired
    public void setXxxConverter(XxxConverter xxxConverter) {
        this.xxxConverter = xxxConverter;
    }

    public void doHandle() {
        System.out.println("XxxHandler.doHandle");
        xxxConverter.doConvert();
    }
}
