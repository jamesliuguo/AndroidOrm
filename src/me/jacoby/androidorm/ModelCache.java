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
package me.jacoby.androidorm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import me.jacoby.androidorm.meta.TableMeta;
import me.jacoby.androidorm.model.Model;

/**
 * 业务模型缓存对象
 * 
 * @author mythyangfan@163.com
 * @since 2013-3-18
 */
public class ModelCache {

	/** 单例对象 **/
	private static final ModelCache SINGLETON = new ModelCache();

	/** 业务模型类集合 **/
	private final List<Class<? extends Model>> modelTypes = new ArrayList<Class<? extends Model>>();

	/** 业务模型对应的表信息映射集合 **/
	private final HashMap<Class<? extends Model>, TableMeta> tableMetas = new HashMap<Class<? extends Model>, TableMeta>();

	/** 构造器 **/
	private ModelCache() {
	}

	/**
	 * 获取业务模型缓存对象
	 * 
	 * @return 业务模型缓存对象
	 */
	public static ModelCache getInstance() {
		return SINGLETON;
	}

	/**
	 * 注册业务模型类型，用来查找基本的数据库需要的表信息
	 * 
	 * @param type
	 *            业务模型类型
	 */
	public void registerType(Class<? extends Model> type) {
		modelTypes.add(type);
	}

	public boolean setup() {

		// 查找对象关系映射，即业务模型对象对应的表信息
		for (int i = 0; i < modelTypes.size(); i++) {
			Class<? extends Model> type = modelTypes.get(i);
			tableMetas.put(type, new TableMeta(type));
		}

		return false;
	}

	/**
	 * 获取TableInfo信息集合
	 * 
	 * @return 表格定义信息集合
	 */
	public Collection<TableMeta> getTableMetas() {
		return tableMetas.values();
	}

	/**
	 * 获取指定类型的表定义信息
	 * 
	 * @param type
	 *            指定业务类型
	 * @return 表定义信息
	 */
	public TableMeta getTableMeta(Class<?> type) {
		return tableMetas.get(type);
	}
}
