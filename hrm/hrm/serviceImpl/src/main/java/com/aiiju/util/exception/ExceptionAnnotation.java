package com.aiiju.util.exception;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ExceptionAnnotation
 * @Description: 自定义注解，用于标注异常信息
 * @author 哪吒
 * @date 2016年9月12日 上午10:25:24
 * 
 */

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionAnnotation {

	// 异常编号
	String key() default "";

	// 异常信息
	String value() default "";
}
