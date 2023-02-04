package cn.maiaimei.framework.swift.validation.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;

public class MT7XxValidationScannerRegistrar extends AbstractValidationScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @SneakyThrows
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String[] configLocations = new String[]{"classpath:validation/mt7xx/**/*.json"};
        Resource[] resources = resolveConfigLocations(configLocations);
        registerBeanDefinitions(registry, resources);
    }
}
