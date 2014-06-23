package com.yummynoodlebar.config.persistence.dataconfig;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Profile("dev")
@Configuration
public class DevDataConfig {

	@Bean
	public DataSource dataSource() throws SQLException {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.H2);
		// builder.addScript("classpath:com/yummynoodlebar/config/sql/schema.sql");
		// builder.addScript("classpath:com/yummynoodlebar/config/sql/test-data.sql");
		return builder.build();
	}
}
