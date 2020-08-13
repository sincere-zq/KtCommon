package com.witaction.netty.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Zhangs on 2017/3/9.
 */


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Desc {
    short key();

    String desc() default "";
}