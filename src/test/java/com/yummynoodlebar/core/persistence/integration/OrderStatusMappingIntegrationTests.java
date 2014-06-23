package com.yummynoodlebar.core.persistence.integration;

import static junit.framework.TestCase.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.GemfireCallback;
import org.springframework.data.gemfire.GemfireOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemstone.gemfire.GemFireCheckedException;
import com.gemstone.gemfire.GemFireException;
import com.gemstone.gemfire.cache.Region;
import com.yummynoodlebar.config.persistence.GemfireConfiguration;
import com.yummynoodlebar.core.persistence.domain.fixture.PersistenceFixture;
import com.yummynoodlebar.persistence.domain.OrderStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GemfireConfiguration.class })
public class OrderStatusMappingIntegrationTests {

	@Autowired
	GemfireOperations yummyTemplate;

	@Before
	public void setup() {
		clear();
	}

	@After
	public void teardown() {
		clear();
	}

	public void clear() {
		yummyTemplate.execute(new GemfireCallback<Object>() {
			@Override
			public Object doInGemfire(Region<?, ?> region) throws GemFireCheckedException, GemFireException {
				region.clear();
				return "completed";
			}
		});
	}

	@Test
	public void thatItemCustomMappingWorks() throws Exception {
		OrderStatus status = PersistenceFixture.startedCooking(UUID.randomUUID());

		yummyTemplate.put(4L, status);

		OrderStatus results = yummyTemplate.findUnique("SELECT * from /YummyNoodleOrder");

		System.out.println("Found " + results.getId());

		assertEquals(status.getId(), results.getId());
		assertEquals(status.getOrderId(), results.getOrderId());
		assertEquals(status.getStatus(), results.getStatus());
	}
}
