package com.witaction.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 协议描述
 */


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Desc {
    short key();

    String desc() default "";
}