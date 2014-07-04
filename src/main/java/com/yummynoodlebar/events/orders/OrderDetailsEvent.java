package com.yummynoodlebar.events.orders;

import com.yummynoodlebar.events.ReadEvent;

import java.util.UUID;

public class OrderDetailsEvent extends ReadEvent {

	private UUID id;
	private OrderDetails orderDetails;

	private OrderDetailsEvent(UUID id) {
		this.id = id;
	}

	public OrderDetailsEvent(UUID id, OrderDetails orderDetails) {
		this.id = id;
		this.orderDetails = orderDetails;
	}

	public UUID getId() {
		return id;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public static OrderDetailsEvent notFound(UUID key) {
		OrderDetailsEvent ev = new OrderDetailsEvent(key);
		ev.entityFound = false;
		return ev;
	}
}
