package com.yummynoodlebar.events.menu;

import java.util.UUID;

import com.yummynoodlebar.events.ReadEvent;

public class MenuItemDetailsEvent extends ReadEvent {
	private UUID key;
	private MenuItemDetails menuItemDetails;

	private MenuItemDetailsEvent() {
	}

	public MenuItemDetailsEvent(UUID key, MenuItemDetails menuItemDetails) {
		this.key = key;
		this.menuItemDetails = menuItemDetails;
	}

	public MenuItemDetails getMenuItemDetails() {
		return menuItemDetails;
	}

	public UUID getKey() {
		return key;
	}

	public static MenuItemDetailsEvent notFound(UUID key) {
		MenuItemDetailsEvent ev = new MenuItemDetailsEvent();
		ev.key = key;
		ev.entityFound = false;
		return ev;
	}
}
