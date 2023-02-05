package cn.maiaimei.framework.swift.validation.config;

import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class ValidationConfigUtils {
    private static final Gson GSON = new Gson();

    private static final String CLASS_NAME = "cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg";
    private static final String MESSAGE_TYPE = "messageType";
    private static final String FIELDS = "fields";
    private static final String SEQUENCES = "sequences";
    private static final String RULES = "rules";

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    public static void registerBeanDefinitions(BeanDefinitionRegistry registry, String messageCatagory, String configLocation) {
        Resource[] resources = resolveConfigLocations(new String[]{configLocation});
        registerBeanDefinitions(registry, messageCatagory, resources);
    }

    public static void registerBeanDefinitions(BeanDefinitionRegistry registry, String messageCatagory, String[] configLocations) {
        Resource[] resources = resolveConfigLocations(configLocations);
        registerBeanDefinitions(registry, messageCatagory, resources);
    }

    private static void registerBeanDefinitions(BeanDefinitionRegistry registry, String messageCatagory, Resource[] resources) {
        for (Resource resource : resources) {
            MessageValidationCfg cfg = getMessageValidationCfg(resource);
            Assert.hasText(cfg.getMessageType(), "type must not be blank, file is " + resource.getFilename());
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CLASS_NAME);
            builder.addPropertyValue(MESSAGE_TYPE, cfg.getMessageType());
            builder.addPropertyValue(FIELDS, cfg.getFields());
            builder.addPropertyValue(SEQUENCES, cfg.getSequences());
            builder.addPropertyValue(RULES, cfg.getRules());
            String beanName = getValidationConfigBeanName(messageCatagory, Objects.requireNonNull(resource.getFilename()));
            registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
    }

    public static String getValidationConfigBeanName(String messageCatagory, String fileName) {
        return String.format("%s%s", messageCatagory, fileName.replaceAll("_", StringUtils.EMPTY).replace(".json", StringUtils.EMPTY));
    }

    private static Resource[] resolveConfigLocations(String[] configLocations) {
        return Stream.of(Optional.ofNullable(configLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private static Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

    public static MessageValidationCfg getMessageValidationCfg(String location) {
        Resource[] resources = getResources(location);
        Resource resource = resources[0];
        return getMessageValidationCfg(resource);
    }

    @SneakyThrows
    private static MessageValidationCfg getMessageValidationCfg(Resource resource) {
        InputStream inputStream = resource.getInputStream();
        String json = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return GSON.fromJson(json, MessageValidationCfg.class);
    }

    private ValidationConfigUtils() {
        throw new UnsupportedOperationException();
    }
}
