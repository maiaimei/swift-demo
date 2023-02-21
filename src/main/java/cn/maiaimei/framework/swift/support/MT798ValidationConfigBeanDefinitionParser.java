package cn.maiaimei.framework.swift.support;

import cn.maiaimei.framework.swift.validation.config.MT798ValidationConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class MT798ValidationConfigBeanDefinitionParser extends AbstractMTValidationConfigBeanDefinitionParser {

    private static final String INDEX_MESSAGE_TYPE_ELEMENT_NAME = "index-message-type";
    private static final String INDEX_MESSAGE_TYPE_PROPERTY = "indexMessageType";
    private static final String SUB_MESSAGE_TYPE_ELEMENT_NAME = "sub-message-type";
    private static final String SUB_MESSAGE_TYPE_PROPERTY = "subMessageType";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        Element idElement = DomUtils.getChildElementByTagName(element, ID_ELEMENT_NAME);
        Element indexMessageTypeElement = DomUtils.getChildElementByTagName(element, INDEX_MESSAGE_TYPE_ELEMENT_NAME);
        Element subMessageTypeElement = DomUtils.getChildElementByTagName(element, SUB_MESSAGE_TYPE_ELEMENT_NAME);
        Element bankToCorporateElement = DomUtils.getChildElementByTagName(element, BANK_TO_CORPORATE_ELEMENT_NAME);
        Element corporateToBankElement = DomUtils.getChildElementByTagName(element, CORPORATE_TO_BANK_ELEMENT_NAME);
        Assert.notNull(idElement, "id element must be present");
        Assert.notNull(subMessageTypeElement, "sub-message-type element must be present");
        Assert.notNull(indexMessageTypeElement, "index-message-type element must be present");
        String id = idElement.getTextContent();
        String indexMessageType = indexMessageTypeElement.getTextContent();
        String subMessageType = subMessageTypeElement.getTextContent();
        Boolean bankToCorporate = bankToCorporateElement != null ? Boolean.parseBoolean(bankToCorporateElement.getTextContent()) : null;
        Boolean corporateToBank = corporateToBankElement != null ? Boolean.parseBoolean(corporateToBankElement.getTextContent()) : null;

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MT798ValidationConfig.class);
        builder.addPropertyValue(INDEX_MESSAGE_TYPE_PROPERTY, indexMessageType);
        builder.addPropertyValue(SUB_MESSAGE_TYPE_PROPERTY, subMessageType);
        builder.addPropertyValue(BANK_TO_CORPORATE_PROPERTY, bankToCorporate);
        builder.addPropertyValue(CORPORATE_TO_BANK_PROPERTY, corporateToBank);
        parseFields(element, builder);
        parseSequences(element, builder);
        parseRules(element, builder);
        parserContext.getRegistry().registerBeanDefinition(id, builder.getBeanDefinition());

        return null;
    }

}
