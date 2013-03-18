
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
package me.jacoby.androidorm.app;

import me.jacoby.androidorm.ModelCache;
import me.jacoby.androidorm.model.Category;
import me.jacoby.androidorm.model.Item;
import android.app.Application;

/**
 * AndroidOrm应用程序对象
 * @author mythyangfan@163.com
 * @since 2013-3-18
 */
public class AndroidOrmApp extends Application {

	private AndroidOrmOpenHelper mDatabaseHelper;
	
	private static final String DB_NAME = "android_orm.sqlite";
	
	private static final int DB_VERSION = 1;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// 初始化模型缓存对象
		ModelCache cache = ModelCache.getInstance();
		cache.registerType(Item.class);
		cache.registerType(Category.class);
		cache.setup();
		
		mDatabaseHelper = new AndroidOrmOpenHelper(this, DB_NAME, DB_VERSION);
		// 打开数据库
		openDatabase();
		
	}
	
	public void openDatabase()  {
		if (null != mDatabaseHelper)  {
			mDatabaseHelper.getWritableDatabase();
		}
	}
}
