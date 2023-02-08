package cn.maiaimei.framework.swift.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.stereotype.Component;

@Component
public class SwiftMTNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        this.registerBeanDefinitionParser("swift-mt", new SwiftMTBeanDefinitionParser());
    }
}
