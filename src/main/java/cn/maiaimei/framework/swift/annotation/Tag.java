package cn.maiaimei.framework.swift.annotation;

import java.lang.annotation.*;

/**
 * SWIFT MT Field Tag
 *
 * @author maiaimei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tag {
    /** tag */
    String value();
}
