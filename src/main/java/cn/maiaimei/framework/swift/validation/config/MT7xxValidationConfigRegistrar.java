package cn.maiaimei.framework.swift.validation.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;

public class MT7xxValidationConfigRegistrar extends AbstractValidationConfig implements ImportBeanDefinitionRegistrar {

    @SneakyThrows
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // TODO: auto scan files
        List<String> paths = Arrays.asList(
                "validation/mt7xx/MT784.json",
                "validation/mt7xx/MT760.json"
        );
        registerBeanDefinitions(registry, paths);
    }
}
