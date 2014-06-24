package com.yummynoodlebar.config.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.yummynoodlebar.persistence.repository.MenuItemRepository;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.repository.OrdersRepository;
import com.yummynoodlebar.persistence.services.MenuPersistenceEventHandler;
import com.yummynoodlebar.persistence.services.MenuPersistenceService;
import com.yummynoodlebar.persistence.services.OrderPersistenceEventHandler;
import com.yummynoodlebar.persistence.services.OrderPersistenceService;

@Configuration
@Import({ JPAConfiguration.class, GemfireConfiguration.class, MongoConfiguration.class })
public class PersistenceConfig {

	@Bean
	public OrderPersistenceService ordersPersistenceService(OrdersRepository ordersRepository, OrderStatusRepository orderStatusRepository) {
		return new OrderPersistenceEventHandler(ordersRepository, orderStatusRepository);
	}

	@Bean
	public MenuPersistenceService menuPersistenceService(MenuItemRepository menuItemRepository) {
		return new MenuPersistenceEventHandler(menuItemRepository);
	}

}
