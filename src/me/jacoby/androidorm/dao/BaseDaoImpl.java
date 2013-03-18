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
package me.jacoby.androidorm.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import me.jacoby.androidorm.ModelCache;
import me.jacoby.androidorm.app.AndroidOrmOpenHelper;
import me.jacoby.androidorm.meta.TableMeta;
import me.jacoby.androidorm.model.Model;

/**
 * 数据操作接口基本实现
 * 
 * @author mythyangfan@163.com
 * @since 2013-3-19
 */
public abstract class BaseDaoImpl<E extends Model, Id> implements Dao<E, Id> {

	private static final String TAG = "AndroidOrm";

	/** 业务模型类型 **/
	private Class<E> modelType;

	/** Android应用程序上下文 **/
	private Context context;

	private AndroidOrmOpenHelper dbHelper;

	private ModelCache modelCache = ModelCache.getInstance();

	public BaseDaoImpl(Context context, Class<E> modelType) {
		this.context = context;
		this.modelType = modelType;
		this.dbHelper = new AndroidOrmOpenHelper(context, "android_orm.sqlite", 1);
	}

	@Override
	public int create(E entity) {
		SQLiteDatabase db = openDatabase(WRITE_MODE);
		TableMeta tableMeta = modelCache.getTableMeta(modelType);
		ContentValues values = entity.modelToContentValues();
		long id = db.insert(tableMeta.getTableName(), null, values);
		// TODO id
		return 0;
	}

	@Override
	public int update(E entity) {
		SQLiteDatabase db = openDatabase(WRITE_MODE);
		TableMeta tableMeta = modelCache.getTableMeta(modelType);
		ContentValues values = entity.modelToContentValues();
		// 执行更新语句
		int row = db.update(tableMeta.getTableName(), values, tableMeta.getIdColumnName() + "=?",
				new String[] { String.valueOf(entity.getId()) });
		return row;
	}

	@Override
	public int delete(E entity) {
		return 0;
	}

	@Override
	public List<E> queryForAll() {
		return null;
	}

	@Override
	public E queryForId(Id id) {
		return null;
	}

	@Override
	public SQLiteDatabase openDatabase(int mode) {
		SQLiteDatabase database = null;
		if (mode == READ_MODE) {
			database = dbHelper.getReadableDatabase();
		} else if (mode == WRITE_MODE) {
			database = dbHelper.getWritableDatabase();
		} else {
			Log.e(TAG, "当前打开数据库的模式不合法");
		}
		return database;
	}

	@Override
	public Class<E> getModelType() {
		return modelType;
	}
}
