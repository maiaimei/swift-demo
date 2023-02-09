package cn.maiaimei.framework.swift.validation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableConfigurationProperties(ValidationConfigProperties.class)
public class ValidationConfig {

    private static final List<String> MT1XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt1xx/**/*.json");
    private static final List<String> MT2XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt2xx/**/*.json");
    private static final List<String> MT3XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt3xx/**/*.json");
    private static final List<String> MT4XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt4xx/**/*.json");
    private static final List<String> MT5XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt5xx/**/*.json");
    private static final List<String> MT6XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt6xx/**/*.json");
    private static final List<String> MT7XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt7xx/**/*.json");
    private static final List<String> MT8XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt8xx/**/*.json");
    private static final List<String> MT9XX_DEFAULT_CONFIG_LOCATIONS = Collections.singletonList("classpath*:validation/mt9xx/**/*.json");

    public static final List<MessageValidationConfig> MESSAGE_VALIDATION_CONFIG_LIST = new ArrayList<>();

    @Autowired
    private ValidationConfigProperties properties;

    @PostConstruct
    public void initMessageValidationCfgList() {
        if (properties.isEnabled()) {
            addConfig(Boolean.TRUE, properties.getMt1xxConfigLocations(), MT1XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt2xxConfigLocations(), MT2XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt3xxConfigLocations(), MT3XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt4xxConfigLocations(), MT4XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt5xxConfigLocations(), MT5XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt6xxConfigLocations(), MT6XX_DEFAULT_CONFIG_LOCATIONS);
            addMT798Config(Boolean.TRUE, properties.getMt7xxConfigLocations(), MT7XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt8xxConfigLocations(), MT8XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(Boolean.TRUE, properties.getMt9xxConfigLocations(), MT9XX_DEFAULT_CONFIG_LOCATIONS);
        } else {
            addConfig(properties.isEnabledMt1xx(), properties.getMt1xxConfigLocations(), MT1XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt2xx(), properties.getMt2xxConfigLocations(), MT2XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt3xx(), properties.getMt3xxConfigLocations(), MT3XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt4xx(), properties.getMt4xxConfigLocations(), MT4XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt5xx(), properties.getMt5xxConfigLocations(), MT5XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt6xx(), properties.getMt6xxConfigLocations(), MT6XX_DEFAULT_CONFIG_LOCATIONS);
            addMT798Config(properties.isEnabledMt7xx(), properties.getMt7xxConfigLocations(), MT7XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt8xx(), properties.getMt8xxConfigLocations(), MT8XX_DEFAULT_CONFIG_LOCATIONS);
            addConfig(properties.isEnabledMt9xx(), properties.getMt9xxConfigLocations(), MT9XX_DEFAULT_CONFIG_LOCATIONS);
        }
    }

    private void addConfig(boolean isEnabledMtXxx, List<String> mtXxxConfigLocations, List<String> mtXxxDefaultConfigLocations) {
        if (!isEnabledMtXxx) {
            return;
        }
        List<String> configLocations = getConfigLocations(mtXxxConfigLocations, mtXxxDefaultConfigLocations);
        Resource[] resources = ValidationConfigUtils.resolveConfigLocations(configLocations.toArray(new String[0]));
        for (Resource resource : resources) {
            MessageValidationConfig cfg = ValidationConfigUtils.getMessageValidationCfg(resource);
            MESSAGE_VALIDATION_CONFIG_LIST.add(cfg);
        }
    }

    private void addMT798Config(boolean isEnabledMtXxx, List<String> mtXxxConfigLocations, List<String> mtXxxDefaultConfigLocations) {
        if (!isEnabledMtXxx) {
            return;
        }
        List<String> configLocations = getConfigLocations(mtXxxConfigLocations, mtXxxDefaultConfigLocations);
        Resource[] resources = ValidationConfigUtils.resolveConfigLocations(configLocations.toArray(new String[0]));
        for (Resource resource : resources) {
            MessageValidationConfig cfg = ValidationConfigUtils.getMT798MessageValidationCfg(resource);
            MESSAGE_VALIDATION_CONFIG_LIST.add(cfg);
        }
    }

    private List<String> getConfigLocations(List<String> mtXxxConfigLocations, List<String> mtXxxDefaultConfigLocations) {
        if (!CollectionUtils.isEmpty(mtXxxConfigLocations)) {
            return mtXxxConfigLocations;
        } else {
            return mtXxxDefaultConfigLocations;
        }
    }
}
