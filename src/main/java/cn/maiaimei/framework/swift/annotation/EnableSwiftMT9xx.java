package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT9xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第9类：现金管理与账务（MT9XX）Bank Statement
 * Enables MT9xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT9xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT9xx {
}
