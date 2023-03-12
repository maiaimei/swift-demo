package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * SWIFT MT Tag
 *
 * @author maiaimei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tag {
    /**
     * tag
     */
    String value();

    /**
     * tags
     */
    String[] tags() default {};
}
