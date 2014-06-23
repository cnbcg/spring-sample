package com.yummynoodlebar.events.menu;

import java.util.UUID;

import com.yummynoodlebar.events.RequestReadEvent;

public class RequestMenuItemDetailsEvent extends RequestReadEvent {
	private UUID key;

	public RequestMenuItemDetailsEvent(UUID key) {
		this.key = key;
	}

	public UUID getKey() {
		return key;
	}
}
