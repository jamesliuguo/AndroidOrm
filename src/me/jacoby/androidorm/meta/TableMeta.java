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
package me.jacoby.androidorm.meta;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;

import android.text.TextUtils;
import android.util.Log;

import me.jacoby.androidorm.annotation.Column;
import me.jacoby.androidorm.annotation.Id;
import me.jacoby.androidorm.annotation.Table;
import me.jacoby.androidorm.model.Model;

/**
 * SQLite数据表元数据信息封装
 * 
 * @author mythyangfan@163.com
 * @since 2013-3-18
 */
public final class TableMeta {

	private static final String TAG = "AndroidOrm";

	/** 表格名称 **/
	private String tableName;

	/** 业务模型类型 **/
	private Class<? extends Model> type;

	/** 主键字段名称 **/
	private String idColumnName;

	/** 表相关的字段集合 **/
	private HashMap<Field, ColumnMeta> columnMetas = new HashMap<Field, ColumnMeta>();

	/**
	 * 业务相关表信息
	 * 
	 * @param type
	 *            业务相关类型
	 */
	public TableMeta(Class<? extends Model> type) {
		final Table tableAnnotation = type.getAnnotation(Table.class);
		if (null != tableAnnotation) {
			this.type = type;
			this.tableName = TextUtils.isEmpty(tableAnnotation.name()) ? type.getSimpleName() : tableAnnotation.name();

			// 遍历业务类型的所有成员，查找表字段信息
			for (Field field : type.getDeclaredFields()) {
				if (field.isAnnotationPresent(Column.class)) {
					final Column columnAnnotation = field.getAnnotation(Column.class);
					ColumnMeta column = new ColumnMeta();
					column.setColumnName(columnAnnotation.name());
					column.setNotNull(columnAnnotation.isNotNull());
					columnMetas.put(field, column);
				} else if (field.isAnnotationPresent(Id.class)) {
					final Id idAnnotation = field.getAnnotation(Id.class);
					IdColumnMeta column = new IdColumnMeta();
					column.setColumnName(idAnnotation.name());
					idColumnName = column.getColumnName();
					columnMetas.put(field, column);
				}
			}
		} else {
			Log.e(TAG, "类型" + type.getName() + "不能找到任何Table元信息定义");
		}

	}

	public String getTableName() {
		return tableName;
	}

	public Class<? extends Model> getType() {
		return type;
	}

	public HashMap<Field, ColumnMeta> getColumnMetas() {
		return columnMetas;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	/**
	 * 获取表格字段对应业务模型的成员域
	 * 
	 * @return 业务模型成员域
	 */
	public Collection<Field> getFields() {
		return columnMetas.keySet();
	}

	/**
	 * 获取业务成员对应的表格字段信息
	 * 
	 * @param field
	 *            业务成员域
	 * @return 表格字段信息
	 */
	public ColumnMeta getColumnMeta(Field field) {
		return columnMetas.get(field);
	}
}
