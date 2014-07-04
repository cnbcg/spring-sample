package com.yummynoodlebar.web.rest.controller.fixture;

import static com.yummynoodlebar.web.rest.controller.fixture.RestDataFixture.customKeyOrderDetails;
import static com.yummynoodlebar.web.rest.controller.fixture.RestDataFixture.standardOrderDetails;

import java.util.Date;
import java.util.UUID;

import com.yummynoodlebar.events.orders.OrderCreatedEvent;
import com.yummynoodlebar.events.orders.OrderDeletedEvent;
import com.yummynoodlebar.events.orders.OrderDetailsEvent;
import com.yummynoodlebar.events.orders.OrderStatusDetails;
import com.yummynoodlebar.events.orders.OrderStatusEvent;

public class RestEventFixtures {

	public static OrderStatusEvent orderStatusNotFound(UUID key) {
		return OrderStatusEvent.notFound(key);
	}

	public static OrderDetailsEvent orderDetailsNotFound(UUID key) {
		return OrderDetailsEvent.notFound(key);
	}

	public static OrderDetailsEvent orderDetailsEvent(UUID key) {
		return new OrderDetailsEvent(key, customKeyOrderDetails(key));
	}

	public static OrderCreatedEvent orderCreated(UUID key) {
		return new OrderCreatedEvent(key, customKeyOrderDetails(key));
	}

	public static OrderDeletedEvent orderDeleted(UUID key) {
		return new OrderDeletedEvent(key, standardOrderDetails());
	}

	public static OrderDeletedEvent orderDeletedFailed(UUID key) {
		return OrderDeletedEvent.deletionForbidden(key, standardOrderDetails());
	}

	public static OrderDeletedEvent orderDeletedNotFound(UUID key) {
		return OrderDeletedEvent.notFound(key);
	}
}