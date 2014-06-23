package com.yummynoodlebar.events.menu;

import java.util.List;

import com.yummynoodlebar.events.ReadEvent;

public class AllMenuItemsEvent extends ReadEvent {
	private List<MenuItemDetails> menuItemDetails;

	public AllMenuItemsEvent(List<MenuItemDetails> menuItemDetails) {
		this.menuItemDetails = menuItemDetails;
	}

	public List<MenuItemDetails> getMenuItemDetails() {
		return menuItemDetails;
	}
}
