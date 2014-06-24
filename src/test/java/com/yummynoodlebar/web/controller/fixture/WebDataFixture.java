package com.yummynoodlebar.web.controller.fixture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yummynoodlebar.events.menu.AllMenuItemsEvent;
import com.yummynoodlebar.events.menu.MenuItemDetails;

public class WebDataFixture {

	public static final String STANDARD 	= "Yummy Noodles";
	public static final String CHEF_SPECIAL = "Special Yummy Noodles";
	public static final String LOW_CAL 		= "Low cal Yummy Noodles";
	
	private static final BigDecimal COST = new BigDecimal("10.99");
	private static final int MINUTES_TO_PREPARE = 5;

	public static AllMenuItemsEvent allMenuItems() {
		return new AllMenuItemsEvent(allMenuItemDetails());
	}

	public static List<MenuItemDetails> allMenuItemDetails() {
		List<MenuItemDetails> menuItemDetails = new ArrayList<MenuItemDetails>();
		menuItemDetails.add(standardMenuItemDetails(STANDARD));
		menuItemDetails.add(standardMenuItemDetails(CHEF_SPECIAL));
		menuItemDetails.add(standardMenuItemDetails(LOW_CAL));
		return menuItemDetails;
	}

	private static MenuItemDetails standardMenuItemDetails(String name) {
		return new MenuItemDetails(UUID.randomUUID(), name, COST, MINUTES_TO_PREPARE);
	}

}
