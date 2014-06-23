package com.yummynoodlebar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.yummynoodlebar.config.persistence.MongoConfiguration;
import com.yummynoodlebar.config.persistence.PersistenceConfig;
import com.yummynoodlebar.core.services.OrderEventHandler;
import com.yummynoodlebar.core.services.OrderService;
import com.yummynoodlebar.persistence.services.OrderPersistenceService;

@Configuration
@Import({ PersistenceConfig.class, MongoConfiguration.class })
public class CoreConfig {

	@Bean
	public OrderService orderService(OrderPersistenceService orderPersistenceService) {
		return new OrderEventHandler(orderPersistenceService);
	}

}
