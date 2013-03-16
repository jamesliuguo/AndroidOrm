package me.jacoby.androidorm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解主要用于描述关系型数据库的表的信息
 * 
 * @author mythyangfan@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

	/**
	 * name属性用于表示表的名称
	 * @return 表的名称
	 */
	String name() default "";
}
