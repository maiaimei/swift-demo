package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * Enables MTs convert and validate capability
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableSwiftMT1xx
@EnableSwiftMT2xx
@EnableSwiftMT3xx
@EnableSwiftMT4xx
@EnableSwiftMT5xx
@EnableSwiftMT6xx
@EnableSwiftMT7xx
@EnableSwiftMT8xx
@EnableSwiftMT9xx
public @interface EnableSwiftMT {
}
