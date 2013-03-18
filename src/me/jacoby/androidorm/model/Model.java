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
package me.jacoby.androidorm.model;

import java.lang.reflect.Field;

import android.content.ContentValues;
import android.util.Log;

import me.jacoby.androidorm.ModelCache;
import me.jacoby.androidorm.meta.ColumnMeta;
import me.jacoby.androidorm.meta.TableMeta;
import me.jacoby.androidorm.util.ReflectionUtils;

/**
 * 业务模型类型超类
 * 
 * @author mythyangfan@163.com
 * @since 2013-3-19
 */
public abstract class Model {

	private static final String TAG = "AndroidOrm";

	private TableMeta tableMeta;

	public Model() {
		tableMeta = ModelCache.getInstance().getTableMeta(getClass());
		// TODO addEntity
	}

	/** 
	 * 获取业务模型主键id
	 * @return 业务模型主键id
	 */
	public abstract long getId();

	/**
	 * 将业务模型对象实体转化成为#android.content.ContentValues
	 * 
	 * @return android.content.ContentValues
	 */
	public ContentValues modelToContentValues() {
		ContentValues values = new ContentValues();
		for (Field field : tableMeta.getFields()) {
			final ColumnMeta column = tableMeta.getColumnMeta(field);
			final String columnName = column.getColumnName();
			Class<?> type = field.getType();
			field.setAccessible(true);

			try {
				Object value = field.get(this);
				if (value == null) {
					values.putNull(columnName);
				} else if (type.equals(byte.class) || type.equals(Byte.class)) {
					values.put(columnName, (Byte) value);
				} else if (type.equals(short.class) || type.equals(Short.class)) {
					values.put(columnName, (Short) value);
				} else if (type.equals(int.class) || type.equals(Integer.class)) {
					values.put(columnName, (Integer) value);
				} else if (type.equals(long.class) || type.equals(Long.class)) {
					values.put(columnName, (Long) value);
				} else if (type.equals(float.class) || type.equals(Float.class)) {
					values.put(columnName, (Float) value);
				} else if (type.equals(double.class) || type.equals(Double.class)) {
					values.put(columnName, (Double) value);
				} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
					values.put(columnName, (Boolean) value);
				} else if (type.equals(char.class) || type.equals(Character.class)) {
					values.put(columnName, value.toString());
				} else if (type.equals(String.class)) {
					values.put(columnName, value.toString());
				} else if (type.equals(byte[].class) || type.equals(Byte[].class)) {
					values.put(columnName, (byte[]) value);
				} else if (ReflectionUtils.isModel(type)) {
					values.put(columnName, ((Model) value).getId());
					// TODO 保存外键
				} else {
					;
					// TODO 其它类型的转化
					Log.d(TAG, "部分类型转化暂未被实现");
				}
				
			} catch (Exception ex) {
				Log.e(TAG, "转化字段域的过程中失败");
			}

		}
		return values;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append(" {\n");
		for (Field field : tableMeta.getFields()) {
			sb.append(field.getName());
			sb.append(": ");
			field.setAccessible(true);
			try {
				sb.append(field.get(this));
			} catch (Exception ex) {
				sb.append("null");
				Log.e(TAG, "不能获取成员域[" + field.getName() + "]的值");
			}
			sb.append(",\n");
		}
		sb.delete(sb.length() - 2, sb.length() - 1);
		sb.append("}\n");
		return sb.toString();
	}
}
