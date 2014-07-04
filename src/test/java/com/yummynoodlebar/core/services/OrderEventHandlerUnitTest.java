package com.yummynoodlebar.core.services;

import static com.yummynoodlebar.core.persistence.domain.fixture.PersistenceFixture.orderReceived;
import static com.yummynoodlebar.core.persistence.domain.fixture.PersistenceFixture.standardOrder;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.DeleteOrderEvent;
import com.yummynoodlebar.events.orders.OrderDeletedEvent;
import com.yummynoodlebar.events.orders.OrderDetails;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.repository.OrdersRepository;
import com.yummynoodlebar.persistence.services.OrderPersistenceEventHandler;

public class OrderEventHandlerUnitTest {

	OrderEventHandler uut;
	@Mock
	OrdersRepository mockOrdersRepository;
	@Mock
	OrderStatusRepository mockOrderStatusRepository;

	@Before
	public void setupUnitUnderTest() {
		MockitoAnnotations.initMocks(this);
		uut = new OrderEventHandler(new OrderPersistenceEventHandler(mockOrdersRepository, mockOrderStatusRepository));
	}

	@Test
	public void addANewOrderToTheSystem() {
		Order order = standardOrder();
		OrderStatus orderStatus = orderReceived(order.getKey());

		when(mockOrdersRepository.save(any(Order.class))).thenReturn(order);
		when(mockOrderStatusRepository.save(any(OrderStatus.class))).thenReturn(orderStatus);

		CreateOrderEvent ev = new CreateOrderEvent(order.toOrderDetails());

		uut.createOrder(ev);

		verify(mockOrdersRepository).save(any(Order.class));
		verify(mockOrderStatusRepository).save(any(OrderStatus.class));
		verifyNoMoreInteractions(mockOrdersRepository);
		verifyNoMoreInteractions(mockOrderStatusRepository);
	}

	@Test
	public void addTwoNewOrdersToTheSystem() {
		Order order = standardOrder();
		OrderStatus orderStatus = orderReceived(order.getKey());

		when(mockOrdersRepository.save(any(Order.class))).thenReturn(order);
		when(mockOrderStatusRepository.save(any(OrderStatus.class))).thenReturn(orderStatus);

		CreateOrderEvent ev = new CreateOrderEvent(order.toOrderDetails());

		uut.createOrder(ev);
		uut.createOrder(ev);

		verify(mockOrdersRepository, times(2)).save(any(Order.class));
		verify(mockOrderStatusRepository, times(2)).save(any(OrderStatus.class));
		verifyNoMoreInteractions(mockOrdersRepository);
		verifyNoMoreInteractions(mockOrderStatusRepository);
	}

	@Test
	public void removeAnOrderFromTheSystemFailsIfNotPresent() {
		UUID key = UUID.randomUUID();

		when(mockOrdersRepository.findOne(key)).thenReturn(null);

		DeleteOrderEvent ev = new DeleteOrderEvent(key);

		OrderDeletedEvent orderDeletedEvent = uut.deleteOrder(ev);

		verify(mockOrdersRepository, never()).delete(ev.getKey());

		assertFalse(orderDeletedEvent.isEntityFound());
		assertFalse(orderDeletedEvent.isDeletionCompleted());
		assertEquals(key, orderDeletedEvent.getKey());

	}

	@Test
	public void removeAnOrderFromTheSystemFailsIfNotPermitted() {

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCanBeDeleted(Boolean.FALSE);
		Order order = Order.fromOrderDetails(orderDetails);
		UUID key = order.getKey();

		when(mockOrdersRepository.findOne(key)).thenReturn(order);

		DeleteOrderEvent ev = new DeleteOrderEvent(key);

		OrderDeletedEvent orderDeletedEvent = uut.deleteOrder(ev);

		verify(mockOrdersRepository, never()).delete(ev.getKey());

		assertTrue(orderDeletedEvent.isEntityFound());
		assertFalse(orderDeletedEvent.isDeletionCompleted());
		assertEquals(key, orderDeletedEvent.getKey());
	}

	@Test
	public void removeAnOrderFromTheSystemWorksIfExists() {

		OrderDetails orderDetails = new OrderDetails();
		Order order = Order.fromOrderDetails(orderDetails);
		order.setCanBeDeleted(Boolean.TRUE);
		UUID key = order.getKey();

		when(mockOrdersRepository.findOne(any(UUID.class))).thenReturn(order);

		DeleteOrderEvent ev = new DeleteOrderEvent(key);

		OrderDeletedEvent orderDeletedEvent = uut.deleteOrder(ev);

		verify(mockOrdersRepository).delete(ev.getKey());

		assertTrue(orderDeletedEvent.isEntityFound());
		assertTrue(orderDeletedEvent.isDeletionCompleted());
		assertEquals(key, orderDeletedEvent.getKey());
	}
}
