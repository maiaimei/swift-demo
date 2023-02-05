package cn.maiaimei.framework.swift.validation.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 第7类：跟单信用证和保函（MT7XX）Documentary Credits and Guarantees
 * {@link ImportBeanDefinitionRegistrar}
 * {@link BeanDefinitionRegistryPostProcessor}
 */
public class MT7xxValidationConfigScannerRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String MESSAGE_CATAGORY = "mt7xx";
    private static final String CONFIG_LOCATION = "classpath*:validation/mt7xx/**/*.json";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ValidationConfigUtils.registerBeanDefinitions(registry, MESSAGE_CATAGORY, CONFIG_LOCATION);
    }
}
