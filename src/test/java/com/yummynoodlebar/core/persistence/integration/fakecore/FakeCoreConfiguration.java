package com.yummynoodlebar.core.persistence.integration.fakecore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yummynoodlebar.core.services.OrderStatusUpdateService;

@Configuration
public class FakeCoreConfiguration {

	@Bean
	OrderStatusUpdateService orderStatusUpdateService() {
		return new CountingOrderStatusService();
	}
}
