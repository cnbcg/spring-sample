package com.yummynoodlebar.core.persistence.integration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yummynoodlebar.config.persistence.GemfireConfiguration;
import com.yummynoodlebar.core.persistence.domain.fixture.PersistenceFixture;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GemfireConfiguration.class })
public class OrderStatusRepositoryIntegrationTests {

	@Autowired
	OrderStatusRepository ordersStatusRepository;

	@Before
	public void setup() {
		ordersStatusRepository.deleteAll();
	}

	@After
	public void teardown() {
		ordersStatusRepository.deleteAll();
	}

	@Test
	public void thatItemIsInsertedIntoRepoWorks() throws Exception {
		UUID orderId = UUID.randomUUID();

		OrderStatus orderStatus = PersistenceFixture.startedCooking(orderId);

		ordersStatusRepository.save(orderStatus);

		OrderStatus retrievedOrderStatus = ordersStatusRepository.findOne(orderStatus.getId());

		assertNotNull(retrievedOrderStatus);
		assertEquals(orderId, retrievedOrderStatus.getOrderId());
	}
}
