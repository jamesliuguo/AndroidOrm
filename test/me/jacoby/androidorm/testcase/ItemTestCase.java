package me.jacoby.androidorm.testcase;

import me.jacoby.androidorm.app.AndroidOrmApp;
import me.jacoby.androidorm.dao.Dao;
import me.jacoby.androidorm.dao.DaoManagerRepo;
import me.jacoby.androidorm.dao.ItemDao;
import me.jacoby.androidorm.model.Category;
import me.jacoby.androidorm.model.Item;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

public class ItemTestCase extends ApplicationTestCase<AndroidOrmApp> {

	public ItemTestCase(Class<AndroidOrmApp> applicationClass) {
		super(AndroidOrmApp.class);
	}

	private DaoManagerRepo daoManagerRepo;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createApplication();
		daoManagerRepo = DaoManagerRepo.getInstance();
		daoManagerRepo.createDao(getContext(), Item.class, ItemDao.class);
	}
	
	@MediumTest
	public void testCreateItem() {
		
		Dao<Item, Long> itemDao = daoManagerRepo.getDao(Item.class);
		
		Item item = new Item();
		item.setName("Computer");
		item.setCategory(new Category());
		
		itemDao.create(item);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
