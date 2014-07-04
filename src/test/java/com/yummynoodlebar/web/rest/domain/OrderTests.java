package com.yummynoodlebar.web.rest.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yummynoodlebar.events.orders.OrderDetails;
import com.yummynoodlebar.web.rest.controller.fixture.RestDataFixture;
import com.yummynoodlebar.web.rest.domain.Order;

public class OrderTests {

	@Before
	public void setup() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	public void thatOrderCanConvertToOrderDetails() {
		Order order = RestDataFixture.standardOrder();

		OrderDetails details = order.toOrderDetails();

		assertEquals(order.getKey(), details.getKey());
		assertEquals(order.getDateTimeOfSubmission(), details.getDateTimeOfSubmission());
		assertEquals(details.getOrderItems().size(), details.getOrderItems().size());
		assertTrue(details.getOrderItems().containsKey(RestDataFixture.YUMMY_ITEM_ID));
		assertEquals(details.getOrderItems().get(RestDataFixture.YUMMY_ITEM_ID), order.getItems().get(RestDataFixture.YUMMY_ITEM_ID));
	}

	@Test
	public void thatOrderCanConvertFromOrderDetails() {
		OrderDetails details = RestDataFixture.standardOrderDetails();

		Order order = Order.fromOrderDetails(details);

		assertEquals(order.getKey(), details.getKey());
		assertEquals(order.getDateTimeOfSubmission(), details.getDateTimeOfSubmission());
		assertEquals(order.getItems().size(), details.getOrderItems().size());
		assertTrue(order.getItems().containsKey(RestDataFixture.YUMMY_ITEM_ID));
		assertEquals(details.getOrderItems().get(RestDataFixture.YUMMY_ITEM_ID), order.getItems().get(RestDataFixture.YUMMY_ITEM_ID));
	}
}
