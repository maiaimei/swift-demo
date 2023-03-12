package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * SWIFT MT Component
 *
 * @author maiaimei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    int index();
}
