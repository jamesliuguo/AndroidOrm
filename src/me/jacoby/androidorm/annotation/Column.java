package me.jacoby.androidorm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Column注解描述了表的字段信息，如表示了字段的名称、字段的类型、长度等
 * 
 * @author mythyangfan@163.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	
	/**
	 * name表示所描述的字段名称
	 * @return 字段名称
	 */
	String name() default "";
}
