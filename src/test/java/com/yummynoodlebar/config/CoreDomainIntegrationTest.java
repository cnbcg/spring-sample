package com.yummynoodlebar.config;

import static junit.framework.TestCase.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.core.services.OrderService;
import com.yummynoodlebar.events.orders.AllOrdersEvent;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.RequestAllOrdersEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { CoreConfig.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CoreDomainIntegrationTest {

	@Autowired
	OrderService orderService;

	@Test
	public void addANewOrderToTheSystem() {
		Order order = new Order(new Date());

		CreateOrderEvent ev = new CreateOrderEvent(order.toOrderDetails());

		orderService.createOrder(ev);

		AllOrdersEvent allOrders = orderService.requestAllOrders(new RequestAllOrdersEvent());

		assertEquals(1, allOrders.getOrdersDetails().size());
	}
}
