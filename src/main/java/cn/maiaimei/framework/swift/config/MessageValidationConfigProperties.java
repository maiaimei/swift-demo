package cn.maiaimei.framework.swift.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** swift mts validation config properties */
@Data
@Component
@ConfigurationProperties(prefix = "swift.mt.validation")
public class MessageValidationConfigProperties {
    /** Enables all mts validation capability */
    private boolean enabled;
    /** Enables MT7xx validation capability */
    private boolean enabledMt7xx;
    /** Enables MT9xx validation capability */
    private boolean enabledMt9xx;
}
