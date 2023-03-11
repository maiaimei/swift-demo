package cn.maiaimei.framework.swift.support;

import cn.maiaimei.framework.swift.util.MTConfigParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class DefaultMTConfigBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        MTConfigParser.parse(element, parserContext.getRegistry());
        return null;
    }

}
