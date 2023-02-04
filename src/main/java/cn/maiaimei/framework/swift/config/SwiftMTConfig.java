package cn.maiaimei.framework.swift.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
@ComponentScan("cn.maiaimei.framework.swift.validation")
public class SwiftMTConfig {
}
