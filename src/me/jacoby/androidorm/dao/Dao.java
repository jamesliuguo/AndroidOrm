
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

import android.database.sqlite.SQLiteDatabase;
import me.jacoby.androidorm.model.Model;

/**
 * 业务模型数据操作接口
 * @author mythyangfan@163.com
 * @since 2013-3-19
 * @param <E> 业务模型 
 * @param <Id> 主键类型
 */
public interface Dao<E extends Model, Id> {

	/** 以只读模式打开SQLite数据库  **/
	public int READ_MODE = 0;
	
	/** 以可写模式打开SQLite数据库  **/
	public int WRITE_MODE = 1;
	
	/**
	 * 创建业务模型并进行持久化
	 * @param entity 业务模型
	 * @return 业务模型的id
	 */
	public int create(E entity);
	
	/**
	 * 更新业务模型对象
	 * @param entity 业务模型 
	 * @return 更新插入的条数，插入异常返回-1
	 */
	public int update(E entity);
	
	/**
	 * 删除业务模型对象
	 * @param entity 业务模型 
	 * @return 删除插入的条数，插入异常返回-1
	 */
	public int delete(E entity);
	
	/**
	 * 查询所有业务模型对象
	 * @return 业务模型集合
	 */
	public List<E> queryForAll();
	
	/**
	 * 通过主键Id查询业务模型对象
	 * @param id 主键id
	 * @return 业务模型
	 */
	public E queryForId(Id id);
	
	/**
	 * 以不同的模式打开SQLite数据库(只读，可写)
	 * @param mode 打开数据库的模式
	 * @return SQLite数据库
	 */
	public SQLiteDatabase openDatabase(int mode);
	
	/**
	 * 返回当前业务模型类型
	 * @return 业务模型类型
	 */
	public Class<E> getModelType();
}
