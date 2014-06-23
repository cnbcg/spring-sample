package com.yummynoodlebar.config.persistence;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.yummynoodlebar.persistence.repository.MenuItemRepository;

@Configuration
@EnableMongoRepositories(basePackages = "com.yummynoodlebar.persistence.repository", includeFilters = @ComponentScan.Filter(value = { MenuItemRepository.class }, type = FilterType.ASSIGNABLE_TYPE))
public class MongoConfiguration {

	@Bean
	public MongoTemplate mongoTemplate(Mongo mongo) throws UnknownHostException {
		return new MongoTemplate(mongo, "yummynoodle");
	}

	@Bean
	public Mongo mongo() throws UnknownHostException {
		return new MongoClient("localhost");
	}
}
