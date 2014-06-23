package com.yummynoodlebar.core.persistence.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.yummynoodlebar.config.persistence.GemfireConfiguration;
import com.yummynoodlebar.core.persistence.integration.fakecore.CountingOrderStatusService;
import com.yummynoodlebar.core.persistence.integration.fakecore.FakeCoreConfiguration;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(classes = { FakeCoreConfiguration.class, GemfireConfiguration.class })
public class OrderStatusNotificationsIntegrationTests {

	@Autowired
	OrderStatusRepository ordersStatusRepository;

	@Autowired
	CountingOrderStatusService orderStatusUpdateService;

	@Before
	public void setup() {
		ordersStatusRepository.deleteAll();
	}

	@After
	public void teardown() {
		ordersStatusRepository.deleteAll();
	}

	@Test
	public void thatCQNotificationsPropogateToCore() throws Exception {

//TODO		CountDownLatch countdown = new CountDownLatch(3);
//		orderStatusUpdateService.setLatch(countdown);
//
//		UUID orderId = UUID.randomUUID();
//
//		ordersStatusRepository.save(PersistenceFixture.orderReceived(orderId));
//		ordersStatusRepository.save(PersistenceFixture.orderReceived(orderId));
//		ordersStatusRepository.save(PersistenceFixture.orderReceived(orderId));
//
//		boolean completedWithinTimeout = countdown.await(5, TimeUnit.SECONDS);
//
//		assertTrue("Did not send enough notifications within timeout", completedWithinTimeout);
	}
}
