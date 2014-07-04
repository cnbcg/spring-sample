package com.yummynoodlebar.core.domain.fixtures;

import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.core.domain.OrderStatus;
import com.yummynoodlebar.events.orders.OrderDetails;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class OrdersFixtures {

	public static final String ORDER_NAME = "My Order";
	public static final UUID YUMMY_ITEM = UUID.randomUUID();

	public static Order standardOrder() {
		Order order = new Order(new Date());

		order.setOrderItems(Collections.singletonMap(YUMMY_ITEM, 12));
		order.setOrderStatus(new OrderStatus(order.getKey(), UUID.randomUUID(), ORDER_NAME, new Date(), "Order Created"));

		return order;
	}

	/*
	 * Twin of the above, to improve readability
	 */
	public static OrderDetails standardOrderDetails() {
		return standardOrder().toOrderDetails();
	}

}
