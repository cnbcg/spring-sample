package com.yummynoodlebar.core.services;

import com.yummynoodlebar.events.menu.AllMenuItemsEvent;
import com.yummynoodlebar.events.menu.CreateMenuItemEvent;
import com.yummynoodlebar.events.menu.MenuItemDetailsEvent;
import com.yummynoodlebar.events.menu.RequestAllMenuItemsEvent;
import com.yummynoodlebar.events.menu.RequestMenuItemDetailsEvent;

public interface MenuService {

	AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent);

	MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent);

	MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent);
}
