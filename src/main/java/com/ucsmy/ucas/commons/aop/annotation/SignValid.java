package com.ucsmy.ucas.commons.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by ucs_leijinming on 2017/4/10.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SignValid {
    String desc() default "身份和安全验证开始...";
}
