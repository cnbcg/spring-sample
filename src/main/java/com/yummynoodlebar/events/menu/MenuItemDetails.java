package com.yummynoodlebar.events.menu;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuItemDetails {

	private UUID key;
	private String name;

	private BigDecimal cost;

	private int minutesToPrepare;

	public MenuItemDetails(UUID key, String name, BigDecimal cost, int minutesToPrepare) {
		this.key = key;
		this.name = name;
		this.cost = cost;
		this.minutesToPrepare = minutesToPrepare;
	}

	public UUID getId() {
		return key;
	}

	public void setId(UUID key) {
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
}
