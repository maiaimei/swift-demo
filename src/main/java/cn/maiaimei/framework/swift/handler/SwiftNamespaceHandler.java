package cn.maiaimei.framework.swift.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.stereotype.Component;

@Component
public class SwiftNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        this.registerBeanDefinitionParser("swift-mt-validation", new SwiftValidationConfigBeanDefinitionParser());
    }
}
