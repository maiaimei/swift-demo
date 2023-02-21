package cn.maiaimei.framework.swift.support;

import cn.maiaimei.framework.swift.validation.config.ComponentInfo;
import cn.maiaimei.framework.swift.validation.config.FieldInfo;
import cn.maiaimei.framework.swift.validation.config.RuleInfo;
import cn.maiaimei.framework.swift.validation.config.SequenceInfo;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.util.CollectionUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractMTValidationConfigBeanDefinitionParser implements BeanDefinitionParser {

    protected static final String ID_ELEMENT_NAME = "id";
    protected static final String BANK_TO_CORPORATE_ELEMENT_NAME = "bank-to-corporate";
    protected static final String BANK_TO_CORPORATE_PROPERTY = "bankToCorporate";
    protected static final String CORPORATE_TO_BANK_ELEMENT_NAME = "corporate-to-bank";
    protected static final String CORPORATE_TO_BANK_PROPERTY = "corporateToBank";

    private static final String FIELDS = "fields";
    private static final String FIELD = "field";
    private static final String COMPONENTS = "components";
    private static final String COMPONENT = "component";
    private static final String SEQUENCES = "sequences";
    private static final String SEQUENCE = "sequence";
    private static final String RULES = "rules";
    private static final String RULE = "rule";
    private static final String NO_ATTRIBUTE = "no";
    private static final String TAG_ATTRIBUTE = "tag";
    private static final String FIELD_NAME_ATTRIBUTE = "field-name";
    private static final String FIELD_NAME_PROPERTY = "fieldName";
    private static final String FORMAT_ATTRIBUTE = "format";
    private static final String PATTERN_ATTRIBUTE = "pattern";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String STATUS_ATTRIBUTE = "status";
    private static final String OPTIONS_ATTRIBUTE = "options";
    private static final String INDEX_ATTRIBUTE = "index";
    private static final String START_INDEX_ATTRIBUTE = "start-index";
    private static final String START_INDEX_PROPERTY = "startIndex";
    private static final String END_INDEX_ATTRIBUTE = "end-index";
    private static final String END_INDEX_PROPERTY = "endIndex";
    private static final String LABEL_ATTRIBUTE = "label";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String EXPRESSION_STRING_ATTRIBUTE = "expression-string";
    private static final String EXPRESSION_STRING_PROPERTY = "expressionString";
    private static final String BEAN_NAME_ATTRIBUTE = "bean-name";
    private static final String BEAN_NAME_PROPERTY = "beanName";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "error-message";
    private static final String ERROR_MESSAGE_PROPERTY = "errorMessage";
    private static final String VERTICAL_LINE = "\\|";

    protected void parseSequences(Element element, BeanDefinitionBuilder builder) {
        parseElements(element, SEQUENCES, SEQUENCE, SEQUENCES, builder, this::parseSequenceInfo);
    }

    protected void parseFields(Element element, BeanDefinitionBuilder builder) {
        parseElements(element, FIELDS, FIELD, FIELDS, builder, this::parseFieldInfo);
    }

    private void parseComponents(Element element, BeanDefinitionBuilder builder) {
        parseElements(element, COMPONENTS, COMPONENT, COMPONENTS, builder, this::parseComponentInfo);
    }

    protected void parseRules(Element element, BeanDefinitionBuilder builder) {
        parseElements(element, RULES, RULE, RULES, builder, this::parseRuleInfo);
    }

    private void parseElements(Element element, String childEleName, String grandsonEleName,
                               String propertyName,
                               BeanDefinitionBuilder builder,
                               Function<Element, BeanDefinition> function) {
        List<Element> childElements = DomUtils.getChildElementsByTagName(element, childEleName);
        if (CollectionUtils.isEmpty(childElements)) {
            return;
        }
        for (Element childElement : childElements) {
            List<Element> elements = DomUtils.getChildElementsByTagName(childElement, grandsonEleName);
            ManagedList<BeanDefinition> beanDefinitions = new ManagedList<>(elements.size());
            for (Element ele : elements) {
                BeanDefinition beanDefinition = function.apply(ele);
                beanDefinitions.add(beanDefinition);
            }
            builder.addPropertyValue(propertyName, beanDefinitions);
        }
    }

    private BeanDefinition parseSequenceInfo(Element element) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SequenceInfo.class);
        builder.addPropertyValue(NAME_ATTRIBUTE, element.getAttribute(NAME_ATTRIBUTE));
        builder.addPropertyValue(STATUS_ATTRIBUTE, element.getAttribute(STATUS_ATTRIBUTE));
        parseFields(element, builder);
        parseRules(element, builder);
        return builder.getBeanDefinition();
    }

    private BeanDefinition parseFieldInfo(Element element) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(FieldInfo.class);
        addPropertyValue(element, builder, NO_ATTRIBUTE, NO_ATTRIBUTE);
        builder.addPropertyValue(TAG_ATTRIBUTE, element.getAttribute(TAG_ATTRIBUTE));
        builder.addPropertyValue(FIELD_NAME_PROPERTY, element.getAttribute(FIELD_NAME_ATTRIBUTE));
        parseFieldComponentInfo(element, builder);
        parseComponents(element, builder);
        parseRules(element, builder);
        return builder.getBeanDefinition();
    }

    private BeanDefinition parseComponentInfo(Element element) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ComponentInfo.class);
        addPropertyValue(element, builder, INDEX_ATTRIBUTE, INDEX_ATTRIBUTE);
        addPropertyValue(element, builder, START_INDEX_PROPERTY, START_INDEX_ATTRIBUTE);
        addPropertyValue(element, builder, END_INDEX_PROPERTY, END_INDEX_ATTRIBUTE);
        addPropertyValue(element, builder, LABEL_ATTRIBUTE, LABEL_ATTRIBUTE);
        parseFieldComponentInfo(element, builder);
        return builder.getBeanDefinition();
    }

    private void parseFieldComponentInfo(Element element, BeanDefinitionBuilder builder) {
        builder.addPropertyValue(FORMAT_ATTRIBUTE, element.getAttribute(FORMAT_ATTRIBUTE));
        builder.addPropertyValue(STATUS_ATTRIBUTE, element.getAttribute(STATUS_ATTRIBUTE));
        addPropertyValue(element, builder, PATTERN_ATTRIBUTE, PATTERN_ATTRIBUTE);
        addPropertyValue(element, builder, TYPE_ATTRIBUTE, TYPE_ATTRIBUTE);
        if (element.hasAttribute(OPTIONS_ATTRIBUTE)) {
            String options = element.getAttribute(OPTIONS_ATTRIBUTE);
            builder.addPropertyValue(OPTIONS_ATTRIBUTE, options.split(VERTICAL_LINE));
        }
    }

    private BeanDefinition parseRuleInfo(Element element) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RuleInfo.class);
        builder.addPropertyValue(EXPRESSION_STRING_PROPERTY, element.getAttribute(EXPRESSION_STRING_ATTRIBUTE));
        builder.addPropertyValue(BEAN_NAME_PROPERTY, element.getAttribute(BEAN_NAME_ATTRIBUTE));
        builder.addPropertyValue(ERROR_MESSAGE_PROPERTY, element.getAttribute(ERROR_MESSAGE_ATTRIBUTE));
        return builder.getBeanDefinition();
    }

    private void addPropertyValue(Element element, BeanDefinitionBuilder builder, String propertyName, String attributeName) {
        if (element.hasAttribute(attributeName)) {
            builder.addPropertyValue(propertyName, element.getAttribute(attributeName));
        }
    }

}
