package com.yummynoodlebar.events.menu;

import java.util.UUID;

import com.yummynoodlebar.events.RequestReadEvent;

public class RequestMenuItemDetailsEvent extends RequestReadEvent {
	private UUID id;

	public RequestMenuItemDetailsEvent(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}
}
