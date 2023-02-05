package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT5xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第5类：证券（MT5XX）Securities
 * Enables MT5xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT5xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT5xx {
}
