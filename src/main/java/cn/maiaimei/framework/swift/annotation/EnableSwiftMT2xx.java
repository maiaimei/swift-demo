package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT2xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第2类：银行头寸调拨（MT2XX）Financial Institution Transfers
 * Enables MT2xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT2xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT2xx {
}
