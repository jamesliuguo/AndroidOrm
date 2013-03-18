package me.jacoby.androidorm.app;

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
import java.lang.reflect.Field;
import java.util.Iterator;

import me.jacoby.androidorm.ModelCache;
import me.jacoby.androidorm.meta.ColumnMeta;
import me.jacoby.androidorm.meta.IdColumnMeta;
import me.jacoby.androidorm.meta.TableMeta;
import me.jacoby.androidorm.type.SQLiteType;
import me.jacoby.androidorm.util.ReflectionUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * SQLite数据库辅助类
 * 
 * @author mythyangfan@163.com
 * @since 2013-3-18
 */
public class AndroidOrmOpenHelper extends SQLiteOpenHelper {

	private static final String TAG = "AndroidOrm";
	
	/**
	 * SQLite数据库是否支持外键特性，自Android(froyo)版本后，都是支持数据库外键的
	 */
	private static final boolean FOREIGH_KEYS_SUPPORTED = Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	
	private ModelCache modelCache = ModelCache.getInstance();
	
	public AndroidOrmOpenHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 打开SQLite3数据库的外键特性
		if (FOREIGH_KEYS_SUPPORTED) {
			db.execSQL("PRAGMA foreigh_keys=ON");
			Log.i(TAG, "当前SQLite数据库支持外键，打开外键特性");
		}
		execTableCreation(db);
	}
	
	// 创建数据库表
	private void execTableCreation(SQLiteDatabase db) {
		// TODO 判断ModelCache是否初始化
		Iterator<TableMeta> iterator = modelCache.getTableMetas().iterator();
		while (iterator.hasNext()) {
			TableMeta table = iterator.next();
			Log.i(TAG, "准备创建表 [" + table.getTableName() + "]的结构");
			String tableDefinition = getTableDefinition(table);
			// 执行表格结构定义语句
			db.execSQL(tableDefinition);
			Log.d(TAG, tableDefinition);
		}
	}

	// 获取表结构定义
	private String getTableDefinition(TableMeta table) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(table.getTableName());
		sql.append(" (");
		
		// 获取表格字段定义语句片断
		for (Field field: table.getFields()) {
			String columnDefinition = getColumnDefinition(table, field);
			sql.append(columnDefinition);
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") ");
		return sql.toString();
	}
	
	private String getColumnDefinition(TableMeta table, Field field) {
		StringBuilder sql = new StringBuilder();
		ColumnMeta column = table.getColumnMeta(field);
		Class<?> type = field.getType();
		
		sql.append(column.getColumnName());
		// TODO SQLite type
		if (SQLiteType.TYPE_MAP.containsKey(type)) {
			sql.append(" " + SQLiteType.TYPE_MAP.get(type).toString());
		}
		
		// 判断是否为Id字段
		if (column instanceof IdColumnMeta) {
			sql.append(" PRIMARY KEY AUTOINCREMENT");
			return sql.toString();
		}
		
		if (column.isNotNull()) {
			sql.append(" NOT NULL");
		}
		
		if (FOREIGH_KEYS_SUPPORTED && ReflectionUtils.isModel(type)) {
			TableMeta typeTable = modelCache.getTableMeta(type);
			sql.append(" REFERENCES ");
			sql.append(typeTable.getTableName());
			sql.append("(" + typeTable.getIdColumnName() + ")");
		}
		
		return sql.toString();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}


}
