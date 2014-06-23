package com.yummynoodlebar.core.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yummynoodlebar.config.persistence.JPAConfiguration;
import com.yummynoodlebar.core.domain.fixtures.OrdersFixtures;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.repository.OrdersRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { JPAConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrdersUnitTest {

	@Autowired
	OrdersRepository ordersRepository;

	@Test
	public void addAndRemoveASingleOrder() {

		Order order = Order.fromOrderDetails(OrdersFixtures.standardOrder().toOrderDetails());
		assertEquals(0, ordersRepository.count());

		ordersRepository.save(order);
		assertEquals(1, ordersRepository.count());

		ordersRepository.delete(order);
		assertEquals(0, ordersRepository.count());

	}

}
