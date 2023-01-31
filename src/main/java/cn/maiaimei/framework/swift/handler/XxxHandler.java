package cn.maiaimei.framework.swift.handler;

import cn.maiaimei.framework.swift.annotation.SwiftHandler;
import cn.maiaimei.framework.swift.converter.XxxConverter;
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
