package cn.maiaimei.framework.swift.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ConditionalOnProperty(name = "swift.mt.validation.enabled-mt9xx", havingValue = "true")
@Configuration
@ImportResource("classpath*:validation/mt9xx/*.xml")
public class MT9xxValidationConfig {
}
