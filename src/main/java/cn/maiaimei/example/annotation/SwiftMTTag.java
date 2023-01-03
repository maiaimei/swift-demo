package cn.maiaimei.example.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwiftMTTag {
    String value();

    int index() default 0;
}
