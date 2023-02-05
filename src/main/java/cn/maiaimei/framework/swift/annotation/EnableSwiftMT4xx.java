package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT4xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第4类：托收（MT4XX）Collections，Cash Letters
 * Enables MT4xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT4xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT4xx {
}
