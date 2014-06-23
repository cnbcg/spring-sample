package com.yummynoodlebar.persistence.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.yummynoodlebar.events.menu.MenuItemDetails;

@Document(collection = "menu")
public class MenuItem {

	@Id
	private UUID key;

	@Field("itemName")
	@Indexed
	private String name;

	private String description;

	private Set<Ingredient> ingredients;

	private BigDecimal cost;

	private int minutesToPrepare;

	public String getDescription() {
		return description;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public int getMinutesToPrepare() {
		return minutesToPrepare;
	}

	public void setMinutesToPrepare(int minutesToPrepare) {
		this.minutesToPrepare = minutesToPrepare;
	}

	public MenuItemDetails toStatusDetails() {
		return new MenuItemDetails(key, name, cost, minutesToPrepare);
	}

	public static MenuItem fromStatusDetails(MenuItemDetails orderStatusDetails) {
		MenuItem item = new MenuItem();

		item.cost = orderStatusDetails.getCost();
		item.key = orderStatusDetails.getId();
		item.minutesToPrepare = orderStatusDetails.getMinutesToPrepare();
		item.name = orderStatusDetails.getName();

		return item;
	}
}