package com.yummynoodlebar.config.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.repository.OrdersRepository;
import com.yummynoodlebar.persistence.services.OrderPersistenceEventHandler;
import com.yummynoodlebar.persistence.services.OrderPersistenceService;

@Configuration
@Import({ JPAConfiguration.class, GemfireConfiguration.class })
public class PersistenceConfig {

	@Bean
	public OrderPersistenceService ordersPersistenceService(OrdersRepository ordersRepository, OrderStatusRepository orderStatusRepository) {
		return new OrderPersistenceEventHandler(ordersRepository, orderStatusRepository);
	}

}
