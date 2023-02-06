package cn.maiaimei.framework.swift.validation.config;

import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public static final List<MessageValidationCfg> MESSAGE_VALIDATION_CONFIG_LIST = new ArrayList<>();

    @Autowired
    private ValidationConfigProperties properties;

    @PostConstruct
    public void initMessageValidationCfgList() {
        List<String> configLocations = getConfigLocations();
        if (CollectionUtils.isEmpty(configLocations)) {
            return;
        }
        Resource[] resources = ValidationConfigUtils.resolveConfigLocations(configLocations.toArray(new String[0]));
        for (Resource resource : resources) {
            MessageValidationCfg cfg = ValidationConfigUtils.getMessageValidationCfg(resource);
            MESSAGE_VALIDATION_CONFIG_LIST.add(cfg);
        }
    }

    private List<String> getConfigLocations() {
        List<String> configLocations = new ArrayList<>();
        if (properties.isEnabled()) {
            List<String> locations = properties.getConfigLocations();
            if (!CollectionUtils.isEmpty(locations)) {
                configLocations.addAll(locations);
            } else {
                configLocations.addAll(MT1XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT2XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT3XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT4XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT5XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT6XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT7XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT8XX_DEFAULT_CONFIG_LOCATIONS);
                configLocations.addAll(MT9XX_DEFAULT_CONFIG_LOCATIONS);
            }
        } else {
            addConfigLocations(configLocations, properties.isEnabledMt1xx(), properties.getMt1xxConfigLocations(), MT1XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt2xx(), properties.getMt2xxConfigLocations(), MT2XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt3xx(), properties.getMt3xxConfigLocations(), MT3XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt4xx(), properties.getMt4xxConfigLocations(), MT4XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt5xx(), properties.getMt5xxConfigLocations(), MT5XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt6xx(), properties.getMt6xxConfigLocations(), MT6XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt7xx(), properties.getMt7xxConfigLocations(), MT7XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt8xx(), properties.getMt8xxConfigLocations(), MT8XX_DEFAULT_CONFIG_LOCATIONS);
            addConfigLocations(configLocations, properties.isEnabledMt9xx(), properties.getMt9xxConfigLocations(), MT9XX_DEFAULT_CONFIG_LOCATIONS);
        }
        return configLocations.stream().distinct().collect(Collectors.toList());
    }

    private void addConfigLocations(List<String> configLocations,
                                    boolean isEnabled, List<String> mtXxxConfigLocations, List<String> mtXxxDefaultConfigLocations) {
        if (isEnabled) {
            if (CollectionUtils.isEmpty(mtXxxConfigLocations)) {
                configLocations.addAll(mtXxxDefaultConfigLocations);
            } else {
                configLocations.addAll(mtXxxConfigLocations);
            }
        }
    }
}
