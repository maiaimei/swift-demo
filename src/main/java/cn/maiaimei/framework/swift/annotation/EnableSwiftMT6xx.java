package cn.maiaimei.framework.swift.annotation;

import cn.maiaimei.framework.swift.validation.config.MT6xxValidationConfigScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 第6类：贵金属和辛迪加（MT6XX）Precious Metals and Syndication
 * Enables MT6xx convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import({
        MT6xxValidationConfigScannerRegistrar.class
})
public @interface EnableSwiftMT6xx {
}
