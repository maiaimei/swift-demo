package cn.maiaimei.framework.swift.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "cn.maiaimei.framework.swift.validation"
})
public class SwiftMTAutoConfiguration {
}
