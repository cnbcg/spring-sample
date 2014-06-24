package com.yummynoodlebar.config;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yummynoodlebar.config.persistence.PersistenceConfig;
import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.core.services.MenuService;
import com.yummynoodlebar.core.services.OrderService;
import com.yummynoodlebar.events.menu.AllMenuItemsEvent;
import com.yummynoodlebar.events.menu.RequestAllMenuItemsEvent;
import com.yummynoodlebar.events.orders.AllOrdersEvent;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.RequestAllOrdersEvent;
import com.yummynoodlebar.persistence.services.MenuPersistenceService;
import com.yummynoodlebar.web.controller.fixture.WebDataFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { CoreConfig.class, PersistenceConfig.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CoreDomainIntegrationTest {

	@Autowired
	OrderService orderService;

	@Autowired
	@InjectMocks
	MenuService menuService;

	@Mock
	MenuPersistenceService menuPersistenceService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		when(menuPersistenceService.requestAllMenuItems(any(RequestAllMenuItemsEvent.class))).thenReturn(WebDataFixture.allMenuItems());
	}

	@Test
	public void thatAllMenuItemsReturned() {
		AllMenuItemsEvent allMenuItems = menuService.requestAllMenuItems(new RequestAllMenuItemsEvent());

		assertEquals(3, allMenuItems.getMenuItemDetails().size());
	}

	@Test
	public void addANewOrderToTheSystem() {
		Order order = new Order(new Date());

		CreateOrderEvent ev = new CreateOrderEvent(order.toOrderDetails());

		orderService.createOrder(ev);

		AllOrdersEvent allOrders = orderService.requestAllOrders(new RequestAllOrdersEvent());

		assertEquals(1, allOrders.getOrdersDetails().size());
	}
}
