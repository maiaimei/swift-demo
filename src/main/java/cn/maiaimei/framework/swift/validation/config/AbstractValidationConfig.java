package cn.maiaimei.framework.swift.validation.config;

import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AbstractValidationConfig {
    private static final Gson GSON = new Gson();

    private static final String CLASS_NAME = "cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg";
    private static final String SUB_MESSAGE_TYPE = "subMessageType";
    private static final String FIELDS = "fields";
    private static final String SEQUENCES = "sequences";
    private static final String RULES = "rules";
    private static final String MT_XXX_MESSAGE_VALIDATION_CFG = "mt%sMessageValidationCfg";

    protected void registerBeanDefinitions(BeanDefinitionRegistry registry, List<String> paths) {
        for (String path : paths) {
            MessageValidationCfg cfg = getMessageValidationCfg(path);
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClassName(CLASS_NAME);
            beanDefinition.getPropertyValues().add(SUB_MESSAGE_TYPE, cfg.getSubMessageType());
            beanDefinition.getPropertyValues().add(FIELDS, cfg.getFields());
            beanDefinition.getPropertyValues().add(SEQUENCES, cfg.getSequences());
            beanDefinition.getPropertyValues().add(RULES, cfg.getRules());
            registry.registerBeanDefinition(String.format(MT_XXX_MESSAGE_VALIDATION_CFG, cfg.getSubMessageType()), beanDefinition);
        }
    }

    @SneakyThrows
    private MessageValidationCfg getMessageValidationCfg(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        String json = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return GSON.fromJson(json, MessageValidationCfg.class);
    }
}
