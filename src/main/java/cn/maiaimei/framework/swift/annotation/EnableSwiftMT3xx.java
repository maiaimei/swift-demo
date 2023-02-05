package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT3xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第3类：外汇买卖和存放款（MT3XX）Foreign Exchange
 * Enables MT3xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT3xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT3xx {
}
