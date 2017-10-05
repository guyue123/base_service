package com.base.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *自定义注解 拦截Controller
 * @classname SystemControllerLog
 * @author hhx
 * @date 2016年6月15日 下午4:54:28   
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface ControllerLog {

	/**
	 * 方法描述
	 * @author tianyasheng
	 * @date 2016年6月17日 下午2:41:50
	 * @return
	 */
	String description();
	/**
	 * 操作类别，1新增，2删除，3修改，4查询，5跳转
	 * @author tianyasheng
	 * @date 2016年6月17日 下午2:41:13
	 * @return
	 */
	String operateType();
}

