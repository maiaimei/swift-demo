package cn.maiaimei.framework.swift.support;

import cn.maiaimei.framework.swift.model.Currencies;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

public class CurrencyBeanDefinitionParser implements BeanDefinitionParser {
    private static final String COUNTRY_ATTRIBUTE = "country";
    private static final String COUNTRY_SHORT_NAME_ATTRIBUTE = "country-short-name";
    private static final String CURRENCY_ATTRIBUTE = "currency";
    private static final String FRACTION_DIGITS_ATTRIBUTE = "fraction-digits";

    private static final String COUNTRY_PROPERTY = "country";
    private static final String COUNTRY_SHORT_NAME_PROPERTY = "countryShortName";
    private static final String CURRENCY_PROPERTY = "currency";
    private static final String FRACTION_DIGITS_PROPERTY = "fractionDigits";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        List<Element> childElements = DomUtils.getChildElements(element);
        if (!CollectionUtils.isEmpty(childElements)) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Currencies.class);
            ManagedList<BeanDefinition> beanDefinitions = new ManagedList<>(childElements.size());
            for (Element childElement : childElements) {
                String country = childElement.getAttribute(COUNTRY_ATTRIBUTE);
                String countryShortName = childElement.getAttribute(COUNTRY_SHORT_NAME_ATTRIBUTE);
                String currency = childElement.getAttribute(CURRENCY_ATTRIBUTE);
                String fractionDigits = childElement.getAttribute(FRACTION_DIGITS_ATTRIBUTE);

                BeanDefinitionBuilder b = BeanDefinitionBuilder.genericBeanDefinition(Currencies.CurrencyInfo.class);
                b.addPropertyValue(COUNTRY_PROPERTY, country);
                b.addPropertyValue(COUNTRY_SHORT_NAME_PROPERTY, countryShortName);
                b.addPropertyValue(CURRENCY_PROPERTY, currency);
                b.addPropertyValue(FRACTION_DIGITS_PROPERTY, fractionDigits);
                beanDefinitions.add(b.getBeanDefinition());
            }
            builder.addPropertyValue("currencies", beanDefinitions);
            parserContext.getRegistry().registerBeanDefinition("currencies", builder.getBeanDefinition());
        }

        return null;
    }
}
