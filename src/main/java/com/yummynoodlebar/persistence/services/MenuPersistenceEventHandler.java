package com.yummynoodlebar.persistence.services;

import java.util.ArrayList;
import java.util.List;

import com.yummynoodlebar.events.menu.AllMenuItemsEvent;
import com.yummynoodlebar.events.menu.CreateMenuItemEvent;
import com.yummynoodlebar.events.menu.MenuItemDetails;
import com.yummynoodlebar.events.menu.MenuItemDetailsEvent;
import com.yummynoodlebar.events.menu.RequestAllMenuItemsEvent;
import com.yummynoodlebar.events.menu.RequestMenuItemDetailsEvent;
import com.yummynoodlebar.persistence.domain.MenuItem;
import com.yummynoodlebar.persistence.repository.MenuItemRepository;

public class MenuPersistenceEventHandler implements MenuPersistenceService {

	private MenuItemRepository menuItemRepository;

	public MenuPersistenceEventHandler(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}

	@Override
	public AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent) {
		Iterable<MenuItem> menuItems = menuItemRepository.findAll();

		List<MenuItemDetails> details = new ArrayList<MenuItemDetails>();

		for (MenuItem item : menuItems) {
			details.add(item.toStatusDetails());
		}

		return new AllMenuItemsEvent(details);
	}

	@Override
	public MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent) {
		MenuItem item = menuItemRepository.findOne(requestMenuItemDetailsEvent.getKey());

		if (item == null) {
			return MenuItemDetailsEvent.notFound(requestMenuItemDetailsEvent.getKey());
		}

		return new MenuItemDetailsEvent(requestMenuItemDetailsEvent.getKey(), item.toStatusDetails());
	}

	@Override
	public MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent) {
		MenuItem item = menuItemRepository.save(MenuItem.fromStatusDetails(createMenuItemEvent.getDetails()));

		return new MenuItemDetailsEvent(item.getKey(), item.toStatusDetails());
	}
}
