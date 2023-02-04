package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * Enables MTs convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableSwiftMT7xx
public @interface EnableSwiftMT {
}
