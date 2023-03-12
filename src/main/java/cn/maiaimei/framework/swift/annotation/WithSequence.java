package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * A marker annotation indicating that a bean is with sequences
 *
 * @author maiaimei
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithSequence {
}
