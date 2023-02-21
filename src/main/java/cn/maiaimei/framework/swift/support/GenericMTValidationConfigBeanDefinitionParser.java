package cn.maiaimei.framework.swift.support;

import cn.maiaimei.framework.swift.validation.config.MessageValidationConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class GenericMTValidationConfigBeanDefinitionParser extends AbstractMTValidationConfigBeanDefinitionParser {

    private static final String MESSAGE_TYPE_ELEMENT_NAME = "message-type";
    private static final String MESSAGE_TYPE_PROPERTY = "messageType";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        Element idElement = DomUtils.getChildElementByTagName(element, ID_ELEMENT_NAME);
        Element messageTypeElement = DomUtils.getChildElementByTagName(element, MESSAGE_TYPE_ELEMENT_NAME);
        Element bankToCorporateElement = DomUtils.getChildElementByTagName(element, BANK_TO_CORPORATE_ELEMENT_NAME);
        Element corporateToBankElement = DomUtils.getChildElementByTagName(element, CORPORATE_TO_BANK_ELEMENT_NAME);
        Assert.notNull(idElement, "id element must be present");
        Assert.notNull(messageTypeElement, "message-type element must be present");
        String id = idElement.getTextContent();
        String messageType = messageTypeElement.getTextContent();
        Boolean bankToCorporate = bankToCorporateElement != null ? Boolean.parseBoolean(bankToCorporateElement.getTextContent()) : null;
        Boolean corporateToBank = corporateToBankElement != null ? Boolean.parseBoolean(corporateToBankElement.getTextContent()) : null;

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageValidationConfig.class);
        builder.addPropertyValue(MESSAGE_TYPE_PROPERTY, messageType);
        builder.addPropertyValue(BANK_TO_CORPORATE_PROPERTY, bankToCorporate);
        builder.addPropertyValue(CORPORATE_TO_BANK_PROPERTY, corporateToBank);
        parseFields(element, builder);
        parseSequences(element, builder);
        parseRules(element, builder);
        parserContext.getRegistry().registerBeanDefinition(id, builder.getBeanDefinition());

        return null;
    }

}
