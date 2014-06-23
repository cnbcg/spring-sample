package com.yummynoodlebar.core.persistence.integration;

import static com.yummynoodlebar.core.persistence.domain.fixture.JPAAssertions.assertTableExists;
import static com.yummynoodlebar.core.persistence.domain.fixture.JPAAssertions.assertTableHasColumn;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yummynoodlebar.config.persistence.JPAConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { JPAConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrderMappingIntegrationTests {

	@Resource(name = "entityManager")
	EntityManager manager;

	@Test
	public void thatItemCustomMappingWorks() throws Exception {
		assertTableExists(manager, "NOODLE_ORDERS");
		assertTableExists(manager, "ORDER_ORDER_ITEMS");

		assertTableHasColumn(manager, "NOODLE_ORDERS", "ORDER_KEY");
		assertTableHasColumn(manager, "NOODLE_ORDERS", "SUBMISSION_DATETIME");
	}

}
