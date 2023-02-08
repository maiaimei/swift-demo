package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwiftMTTag {
    /**
     * tag
     */
    String value();

    /**
     * component index
     */
    int index() default -1;
}
