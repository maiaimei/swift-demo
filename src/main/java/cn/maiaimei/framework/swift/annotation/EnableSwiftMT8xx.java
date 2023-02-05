package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT8xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第8类：旅行支票（MT8XX）Traveler’s Cheque
 * Enables MT8xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT8xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT8xx {
}
