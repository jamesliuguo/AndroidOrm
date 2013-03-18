package me.jacoby.androidorm.dao;

import android.content.Context;
import me.jacoby.androidorm.model.Item;

public class CategoryDao extends BaseDaoImpl<Item, Long> {

	public CategoryDao(Context context, Class<Item> modelType) {
		super(context, modelType);
	}
}
