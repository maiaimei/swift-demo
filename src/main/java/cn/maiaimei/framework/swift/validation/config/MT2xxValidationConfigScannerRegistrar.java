package cn.maiaimei.framework.swift.validation.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MT2xxValidationConfigScannerRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String MESSAGE_CATAGORY = "mt2xx";
    private static final String CONFIG_LOCATION = "classpath*:validation/mt2xx/**/*.json";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ValidationConfigUtils.registerBeanDefinitions(registry, MESSAGE_CATAGORY, CONFIG_LOCATION);
    }
}
