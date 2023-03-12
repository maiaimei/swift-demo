package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * SWIFT MT Sequence
 *
 * @author maiaimei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sequence {
    /**
     * sequence name
     */
    String value();

    /**
     * sequence index
     */
    int index() default 0;
}
