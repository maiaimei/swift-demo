package cn.maiaimei.framework.swift.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "swift.mt.validation")
public class SwiftMtValidationConfigProperties {
    private boolean isSimpleLabel;
    private boolean enabledMt7xx;
    private boolean enabledMt9xx;
}
