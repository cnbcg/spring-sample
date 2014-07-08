package com.yummynoodlebar.persistence.integration;

import static com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.standardOrder;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yummynoodlebar.config.persistence.JPAConfiguration;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture;
import com.yummynoodlebar.persistence.repository.OrdersRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { JPAConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrdersRepositoryIntegrationTests {

	@Autowired
	OrdersRepository ordersRepository;

	@Test
	public void thatItemIsInsertedIntoRepoWorks() throws Exception {
		Order order = standardOrder();

		ordersRepository.save(order);
		
		Order retrievedOrder = ordersRepository.findOne(order.getKey());

		assertNotNull(retrievedOrder);
		assertEquals(order.getKey(), retrievedOrder.getKey());
		assertEquals(order.getOrderItems().get(PersistenceFixture.MENU_ID_YUMMY1), retrievedOrder.getOrderItems().get(PersistenceFixture.MENU_ID_YUMMY1));
	}

}
