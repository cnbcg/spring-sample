package com.yummynoodlebar.core.persistence.domain.fixture;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import com.yummynoodlebar.events.orders.OrderDetails;
import com.yummynoodlebar.persistence.domain.Ingredient;
import com.yummynoodlebar.persistence.domain.MenuItem;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.domain.OrderStatus;

public class PersistenceFixture {
	
	public static final String ORDER_NAME = "My Order";
	public static final UUID MENU_ID_YUMMY1 = UUID.randomUUID();
	public static final UUID MENU_ID_YUMMY3 = UUID.randomUUID();
	public static final UUID MENU_ID_YUMMY5 = UUID.randomUUID();
	public static final UUID MENU_ID_YUMMY16 = UUID.randomUUID();
	
	public static MenuItem standardItem() {
		MenuItem item = new MenuItem();
		item.setId(UUID.randomUUID());
		item.setDescription("Peanutty Noodles, perfect for every occasion.");
		item.setName("Yummy Noodles");
		item.setCost(new BigDecimal("12.99"));
		item.setIngredients(new HashSet<Ingredient>(Arrays.asList(new Ingredient("Noodles", "Crisp, lovely noodles"), new Ingredient("Egg",
				"Used in the noodles"), new Ingredient("Peanuts", "A Nut"))));

		return item;
	}

	public static MenuItem eggFriedRice() {
		MenuItem item = new MenuItem();
		item.setId(UUID.randomUUID());
		item.setDescription("Rice, Egg Fried");
		item.setName("Yummy Rice");
		item.setCost(new BigDecimal("12.99"));
		item.setIngredients(new HashSet<Ingredient>(Arrays.asList(new Ingredient("Rice", "Straight White Rice"),
				new Ingredient("Egg", "Chicken Eggs"))));

		return item;
	}

	public static Order standardOrder() {

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setDateTimeOfSubmission(new Date());

		Map<UUID, Integer> items = new HashMap<UUID, Integer>();

		items.put(MENU_ID_YUMMY1, 15);
		items.put(MENU_ID_YUMMY3, 12);
		items.put(MENU_ID_YUMMY5, 7);

		orderDetails.setOrderItems(items);

		return Order.fromOrderDetails(orderDetails);
	}

	public static Order yummy16Order() {

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setDateTimeOfSubmission(new Date());

		Map<UUID, Integer> items = new HashMap<UUID, Integer>();

		items.put(MENU_ID_YUMMY16, 22);

		orderDetails.setOrderItems(items);

		return Order.fromOrderDetails(orderDetails);
	}

	public static OrderStatus orderReceived(UUID orderId) {
		return new OrderStatus(orderId, UUID.randomUUID(), ORDER_NAME, new Date(), "Order Received");
	}

	public static OrderStatus startedCooking(UUID orderId) {
		return new OrderStatus(orderId, UUID.randomUUID(), ORDER_NAME, new Date(), "Started Cooking");
	}

	public static OrderStatus finishedCooking(UUID orderId) {
		return new OrderStatus(orderId, UUID.randomUUID(), ORDER_NAME, new Date(), "Finished Cooking");
	}
}
