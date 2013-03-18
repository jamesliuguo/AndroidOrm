package me.jacoby.androidorm.model;

import me.jacoby.androidorm.annotation.Column;
import me.jacoby.androidorm.annotation.Id;
import me.jacoby.androidorm.annotation.Table;

@Table(name = "Item")
public class Item extends Model{

	@Id(name = "Id")
	private long id;

	@Column(name = "Name", isNotNull = true)
	private String name;

	@Column(name = "Category_Id")
	private Category category;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
