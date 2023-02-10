package cn.maiaimei.framework.swift.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({
        "cn.maiaimei.framework.swift"
})
@ImportResource("classpath*:validation/**/*.xml")
public class SwiftAutoConfiguration {
}
