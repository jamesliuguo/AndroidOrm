package me.jacoby.androidorm.model;

import java.util.List;

import me.jacoby.androidorm.annotation.Column;
import me.jacoby.androidorm.annotation.Id;
import me.jacoby.androidorm.annotation.Table;

@Table
public class Category extends Model{

	@Id
	private long id;
	
	@Column(name="Name")
	private String name;
	
	private List<Item> items;

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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
