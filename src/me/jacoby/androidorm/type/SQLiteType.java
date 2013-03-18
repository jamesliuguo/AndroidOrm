
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
package me.jacoby.androidorm.type;

import java.util.HashMap;

/**
 * SQLite数据类型
 * @author mythyangfan@163.com
 * @since 2013-3-19
 */
public enum SQLiteType {

	/**
	 * SQLite整型类型
	 */
	INTEGER, 
	/**
	 * SQLite浮点型
	 */
	REAL, 
	/**
	 * SQLite普通文本类型
	 */
	TEXT, 
	/**
	 * SQLite大文本类型，块字段
	 */
	BLOB;
	
	/**
	 * 数据类型映射
	 */
	public static final HashMap<Class<?>, SQLiteType> TYPE_MAP = new HashMap<Class<?>, SQLiteType>();

	static {
		TYPE_MAP.put(byte.class, INTEGER);
		TYPE_MAP.put(short.class, INTEGER);
		TYPE_MAP.put(int.class, INTEGER);
		TYPE_MAP.put(long.class, INTEGER);
		TYPE_MAP.put(boolean.class, INTEGER);
		TYPE_MAP.put(Byte.class, INTEGER);
		TYPE_MAP.put(Short.class, INTEGER);
		TYPE_MAP.put(Long.class, INTEGER);
		TYPE_MAP.put(Boolean.class, INTEGER);
		TYPE_MAP.put(float.class, REAL);
		TYPE_MAP.put(double.class, REAL);
		TYPE_MAP.put(Float.class, REAL);
		TYPE_MAP.put(Double.class, REAL);
		TYPE_MAP.put(char.class, TEXT);
		TYPE_MAP.put(Character.class, TEXT);
		TYPE_MAP.put(String.class, TEXT);
		TYPE_MAP.put(byte[].class, BLOB);
		TYPE_MAP.put(Byte[].class, BLOB);
	}
}
