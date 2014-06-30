package com.yummynoodlebar.web.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Basket  {

	private Map<UUID, MenuItem> items = new HashMap<UUID, MenuItem>();

	
	public Basket() {
		
	}

	public Basket(Map<UUID, MenuItem> items) {
		this.items = items;
	}

	
	public MenuItem add(MenuItem item) {
		items.put(item.getId(), item);
		return item;
	}

	
	public void delete(UUID key) {
		items.remove(key);
	}

	
	public MenuItem findById(String key) {
		for (MenuItem item : items.values()) {
			if (item.getId().equals(key)) {
				return item;
			}
		}
		return null;
	}

	
	public List<MenuItem> findAll() {
		return new ArrayList<MenuItem>(items.values());
	}
	
	public List<MenuItem> getItems() {
		return findAll();
	}
	
	public int getSize() {
		return items.size();
	}
}
