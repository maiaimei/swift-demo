package cn.maiaimei.framework.swift.support;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.stereotype.Component;

@Component
public class SwiftNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        this.registerBeanDefinitionParser("swift-mt-validation", new GenericMTValidationConfigBeanDefinitionParser());
        this.registerBeanDefinitionParser("swift-mt798-validation", new MT798ValidationConfigBeanDefinitionParser());
    }
}
