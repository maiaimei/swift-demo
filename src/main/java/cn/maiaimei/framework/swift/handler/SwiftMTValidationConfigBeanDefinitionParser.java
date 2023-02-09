package cn.maiaimei.framework.swift.handler;

import cn.maiaimei.framework.swift.validation.config.MessageValidationConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SwiftMTValidationConfigBeanDefinitionParser implements BeanDefinitionParser {

    private static final String ID = "id";
    private static final String MESSAGE_TYPE_ELEMENT = "message-type";
    private static final String MESSAGE_TYPE_PROPERTY = "messageType";
    private static final String IS_B2C_ELEMENT = "is-b2c";
    private static final String IS_B2C_PROPERTY = "isB2C";
    private static final String FIELDS = "fields";
    private static final String SEQUENCES = "sequences";
    private static final String RULES = "rules";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = getElementTextContent(element, ID);
        String messageType = getElementTextContent(element, MESSAGE_TYPE_ELEMENT);
        String isB2C = getElementTextContent(element, IS_B2C_ELEMENT);

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageValidationConfig.class);
        builder.addPropertyValue(IS_B2C_PROPERTY, isB2C);
        builder.addPropertyValue(MESSAGE_TYPE_PROPERTY, messageType);
        parseElements(builder, element, FIELDS);
        parserContext.getRegistry().registerBeanDefinition(id, builder.getBeanDefinition());

        return null;
    }

    private void parseElements(BeanDefinitionBuilder builder, Element element, String elementTagName) {
        NodeList childNodes = element.getChildNodes();
        if (childNodes == null || childNodes.getLength() <= 0) {
            return;
        }
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element childNode = (Element) childNodes.item(i);
            String no = childNode.getAttribute("no");
            System.out.println();
        }
    }

    private String getElementTextContent(Element element, String elementTagName) {
        NodeList idElements = element.getElementsByTagName(elementTagName);
        Node node = idElements.item(0);
        return node.getTextContent();
    }
}
