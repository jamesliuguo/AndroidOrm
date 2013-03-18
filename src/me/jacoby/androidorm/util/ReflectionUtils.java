
/*
* Copyright (C) 2013 Jacob Yang
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package me.jacoby.androidorm.util;


import me.jacoby.androidorm.annotation.Table;
import me.jacoby.androidorm.meta.IdColumnMeta;
import me.jacoby.androidorm.model.Model;

/**
 * 专用于Android反射的辅助类
 * @author mythyangfan@163.com
 * @since 2013-3-19
 */
public final class ReflectionUtils {

	/** 私有构造器   **/
	private ReflectionUtils() {
	}
	
	/**
	 * 判断类型是否为IdColumnMeta
	 * @param type 类型
	 * @return 是否方IdColumnMeta
	 */
 	public static boolean isIdColumn(Class<?> type) {
 		return type.equals(IdColumnMeta.class);
 	}
	
	/**
	 * 判断是否为一个model(@see Model)类型的类，同时也包含表信息定义
	 * @param type 类型信息
	 * @return 是否为model类型
	 */
	public static boolean isModel(Class<?> type) {
		return isSubClassOf(type, Model.class) && hasTableMeta(type);
	}
	
	/**
	 * 判断类型信息是否包含表信息定义
	 * @param type 类型信息
	 * @return 是否包含表定义
	 */
	public static boolean hasTableMeta(Class<?> type) {
		return type.isAnnotationPresent(Table.class);
	}
	
	/**
	 * 判断类型是否为某个超类的派生类
	 * @param type 类型信息
	 * @param superType 超类信息
	 * @return 是否为子类信息
	 */
	public static boolean isSubClassOf(Class<?> type, Class<?> superType) {
		return superType.isAssignableFrom(type);
	}
}
