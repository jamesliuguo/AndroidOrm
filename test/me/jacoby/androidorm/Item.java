package me.jacoby.androidorm;

import me.jacoby.androidorm.annotation.Column;
import me.jacoby.androidorm.annotation.Table;

@Table(name = "Item")
public class Item {

	@Column(name = "Name")
	public String name;
}
