package cn.maiaimei.framework.swift.validation.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 第9类：现金管理与账务（MT9XX）Bank Statement
 */
public class MT9xxValidationConfigScannerRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String MESSAGE_CATAGORY = "mt9xx";
    private static final String CONFIG_LOCATION = "classpath*:validation/mt9xx/**/*.json";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ValidationConfigUtils.registerBeanDefinitions(registry, MESSAGE_CATAGORY, CONFIG_LOCATION);
    }
}

