package me.jacoby.androidorm.dao;

import android.content.Context;
import me.jacoby.androidorm.model.Item;

public class ItemDao extends BaseDaoImpl<Item, Long> {

	public ItemDao(Context context, Class<Item> modelType) {
		super(context, modelType);
	}
}
