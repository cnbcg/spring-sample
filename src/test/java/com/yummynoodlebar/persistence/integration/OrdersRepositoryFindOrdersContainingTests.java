package com.yummynoodlebar.persistence.integration;

import static com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.standardOrder;
import static com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.yummy16Order;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;

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
public class OrdersRepositoryFindOrdersContainingTests {

	@Autowired
	OrdersRepository ordersRepository;

	@Autowired
	EntityManager entityManager;

	@Test
	public void thatSearchingForOrdesContainingWorks() throws Exception {

		ordersRepository.save(standardOrder());
		ordersRepository.save(standardOrder());
		ordersRepository.save(yummy16Order());
		ordersRepository.save(yummy16Order());

		List<Order> retrievedOrders = ordersRepository.findOrdersContaining(PersistenceFixture.MENU_ID_YUMMY16);

		assertNotNull(retrievedOrders);
		assertEquals(2, retrievedOrders.size());
		assertEquals(22, (int) retrievedOrders.get(0).getOrderItems().get(PersistenceFixture.MENU_ID_YUMMY16));

		retrievedOrders = ordersRepository.findOrdersContaining(PersistenceFixture.MENU_ID_YUMMY3);

		assertNotNull(retrievedOrders);
		assertEquals(2, retrievedOrders.size());
		assertEquals(12, (int) retrievedOrders.get(0).getOrderItems().get(PersistenceFixture.MENU_ID_YUMMY3));
	}

}
