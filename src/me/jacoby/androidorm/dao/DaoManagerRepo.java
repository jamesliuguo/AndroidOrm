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

import java.lang.reflect.Constructor;
import java.util.HashMap;

import me.jacoby.androidorm.model.Model;

import android.content.Context;
import android.util.Log;

/**
 * 数据操作接口管理仓库(DAO管理仓库)
 * 
 * @author mythyangfan@163.com
 * @since 2013-3-19
 */
public class DaoManagerRepo {

	private static final String TAG = "AndroidOrm";

	/** 数据操作管理仓库单例 **/
	private static volatile DaoManagerRepo singleton = null;

	/** 同步锁 **/
	private static Object lock = new Object();

	/** 所有创建好的DAO实例 **/
	@SuppressWarnings("rawtypes")
	private final HashMap<Class<? extends Model>, Dao> daoInstances = new HashMap<Class<? extends Model>, Dao>();

	/** 私有构造器 **/
	private DaoManagerRepo() {
	}

	/**
	 * 数据操作管理仓库单例
	 * 
	 * @return DAO管理仓库
	 */
	public static DaoManagerRepo getInstance() {
		if (null == singleton) {
			synchronized (lock) {
				singleton = new DaoManagerRepo();
			}
		}
		return singleton;
	}

	/**
	 * 创建某种数据操作接口，并返回单例
	 * 
	 * @param context
	 *            Android上下文对象
	 * @param modelType
	 *            业务模型类型
	 * @param daoType
	 *            DAO操作接口类型
	 */
	@SuppressWarnings("rawtypes")
	public void createDao(Context context, Class<? extends Model> modelType, Class<? extends Dao> daoType) {
		if (!daoInstances.containsKey(modelType)) {
			try {
				Constructor constructor = daoType.getConstructor(Context.class, Class.class);
				daoInstances.put(modelType, (Dao) constructor.newInstance(context, modelType));
			} catch (Exception ex) {
				Log.e(TAG, "创建DAO操作实例失败");
			}
		} else {
			Log.w(TAG, "已经有DAO实例注册在[" + modelType.getName() + "]类型上了");
		}
	}
	
	/**
	 * 从已经创建的DAO实例中获取指定类型的DAO
	 * @param type 业务模型类型
	 * @return 数据操作DAO
	 */
	@SuppressWarnings("unchecked")
	public <E extends Model, Id> Dao<E, Id> getDao(Class<? extends Model> type) {
		return daoInstances.get(type);
	} 
}
