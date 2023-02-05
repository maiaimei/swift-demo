package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT7xxValidationConfigScannerRegistrar;
import cn.maiaimei.framework.swift.validation.engine.MT798ValidationEngine;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第7类：跟单信用证和保函（MT7XX）Documentary Credits and Guarantees
 * Enables MT798 convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT7xxValidationConfigScannerRegistrar.class,
        MT798ValidationEngine.class
})
public @interface EnableSwiftMT7xx {
}
