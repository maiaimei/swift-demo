package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT1xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第1类：客户汇款及支票（MT1XX）Customer Transfer
 * Enables MT1xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT1xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT1xx {
}
