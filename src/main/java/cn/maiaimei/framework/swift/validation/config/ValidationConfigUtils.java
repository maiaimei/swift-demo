package cn.maiaimei.framework.swift.validation.config;

import cn.maiaimei.framework.swift.validation.config.mt.mt7xx.MT798ValidationConfig;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Stream;

public class ValidationConfigUtils {
    private static final Gson GSON = new Gson();

    private static final ResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    public static Resource[] resolveConfigLocations(String[] configLocations) {
        return Stream.of(Optional.ofNullable(configLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    public static Resource[] getResources(String location) {
        try {
            return RESOURCE_RESOLVER.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

    public static MessageValidationConfig getMessageValidationCfg(String configLocation) {
        Resource[] resources = getResources(configLocation);
        return getMessageValidationCfg(resources[0]);
    }

    @SneakyThrows
    public static MessageValidationConfig getMessageValidationCfg(Resource resource) {
        return resourceToMessageValidationCfg(resource, MessageValidationConfig.class);
    }

    public static MessageValidationConfig getMT798MessageValidationCfg(String configLocation) {
        Resource[] resources = getResources(configLocation);
        return getMT798MessageValidationCfg(resources[0]);
    }

    @SneakyThrows
    public static MessageValidationConfig getMT798MessageValidationCfg(Resource resource) {
        return resourceToMessageValidationCfg(resource, MT798ValidationConfig.class);
    }

    @SneakyThrows
    private static <T> T resourceToMessageValidationCfg(Resource resource, Class<T> classOfT) {
        InputStream inputStream = resource.getInputStream();
        String json = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return GSON.fromJson(json, classOfT);
    }

    private ValidationConfigUtils() {
        throw new UnsupportedOperationException();
    }
}
