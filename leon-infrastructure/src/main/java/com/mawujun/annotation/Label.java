package com.mawujun.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用方式
 * @Label(message='名称')，
 * 这样就可以从
 * private String name;
 * 
 * 默认的话就以字段的名称作为列明，例如如果没有定义@Label，那上面的返回的就是name
 * @author mawujun
 *
 */
@Target({ METHOD, FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented  
@Inherited
public @interface Label {
	String id() default "";//指定从properties文件中获取数据的key,用来应对国际化，优先级比message高，党properties中有的时候，先显示properties中的neirong

	//String message() default "{com.mawujun.annotation.message}";//默认的名称获取地址
	String name() default "请填写";

}
