package cn.maiaimei.framework.swift.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ConditionalOnProperty(name = "swift.mt.validation.enabled-mt7xx", havingValue = "true")
@Configuration
@ImportResource("classpath*:validation/mt7xx/*.xml")
public class MT7xxValidationConfig {
}
