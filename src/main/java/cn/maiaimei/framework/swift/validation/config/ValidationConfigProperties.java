package cn.maiaimei.framework.swift.validation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "swift.validation")
public class ValidationConfigProperties {
    private boolean enabled;
    private List<String> configLocations;

    private boolean enabledMt1xx;
    private List<String> mt1xxConfigLocations;

    private boolean enabledMt2xx;
    private List<String> mt2xxConfigLocations;

    private boolean enabledMt3xx;
    private List<String> mt3xxConfigLocations;

    private boolean enabledMt4xx;
    private List<String> mt4xxConfigLocations;

    private boolean enabledMt5xx;
    private List<String> mt5xxConfigLocations;

    private boolean enabledMt6xx;
    private List<String> mt6xxConfigLocations;

    private boolean enabledMt7xx;
    private List<String> mt7xxConfigLocations;

    private boolean enabledMt8xx;
    private List<String> mt8xxConfigLocations;

    private boolean enabledMt9xx;
    private List<String> mt9xxConfigLocations;
}
