package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT7xxValidationConfigRegistrar;
import cn.maiaimei.framework.swift.validation.engine.MT798ValidationEngine;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables MT798 convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
        MT7xxValidationConfigRegistrar.class,
        MT798ValidationEngine.class
})
public @interface EnableSwiftMT7xx {
}
