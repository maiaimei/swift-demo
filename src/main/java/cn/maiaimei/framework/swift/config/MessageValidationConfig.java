package cn.maiaimei.framework.swift.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ConditionalOnProperty(name = "swift.mt.validation.enabled", havingValue = "true")
@ImportResource("classpath*:validation/**/*.xml")
public class MessageValidationConfig {}
