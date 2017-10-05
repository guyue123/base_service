package com.base.common.permission.annatation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 权限注解
 * @author <a href="mailto:zhuzhoucheng@base.com.cn">朱周城</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年6月24日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented 
public @interface Permission {
    public enum ReturnType {page,json};
    /**权限值*/
    String pkey();
    /**是否验证*/
    boolean validate() default true;
    /**返回类型*/
    ReturnType returnType() default ReturnType.json;
    
}
