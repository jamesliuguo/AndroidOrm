package me.jacoby.androidorm.ui;

import me.jacoby.androidorm.R;
import me.jacoby.androidorm.dao.Dao;
import me.jacoby.androidorm.dao.DaoManagerRepo;
import me.jacoby.androidorm.dao.ItemDao;
import me.jacoby.androidorm.model.Category;
import me.jacoby.androidorm.model.Item;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		DaoManagerRepo daoManagerRepo = DaoManagerRepo.getInstance();
		daoManagerRepo.createDao(this, Item.class, ItemDao.class);
		Dao<Item, Long> dao = daoManagerRepo.getDao(Item.class);
		Item item = new Item();
		item.setName("Computer");
		item.setCategory(new Category());
		
		dao.create(item);
	}
}
