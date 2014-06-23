package com.yummynoodlebar.config.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.GemfireTransactionManager;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.GemfireCacheManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gemstone.gemfire.cache.Cache;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.services.StatusUpdateGemfireNotificationListener;

@Configuration
@EnableTransactionManagement
@EnableGemfireRepositories(basePackages = "com.yummynoodlebar.persistence.repository", includeFilters = @ComponentScan.Filter(value = { OrderStatusRepository.class }, type = FilterType.ASSIGNABLE_TYPE))
public class GemfireConfiguration {

	@Bean
	public CacheFactoryBean cacheFactoryBean() {
		CacheFactoryBean cacheFactoryBean = new CacheFactoryBean();
		cacheFactoryBean.setUseBeanFactoryLocator(false);
		return cacheFactoryBean;
	}

	@Bean
	public LocalRegionFactoryBean<String, OrderStatus> localRegionFactory(final Cache cache) {
		return new LocalRegionFactoryBean<String, OrderStatus>() {
			{
				setCache(cache);
				setName("YummyNoodleOrder");
				setClose(false);
			}
		};
	}

	@Bean
	GemfireTemplate gemfireTemplate(LocalRegionFactoryBean<String, OrderStatus> localRegionFactoryBean) throws Exception {
		GemfireTemplate gemfireTemplate = new GemfireTemplate(localRegionFactoryBean.getObject());
		return gemfireTemplate;
	}

	@Bean
	public GemfireCacheManager cacheManager(final Cache gemfireCache) throws Exception {
		return new GemfireCacheManager() {
			{
				setCache(gemfireCache);
			}
		};
	}

//	@Bean
//	public StatusUpdateGemfireNotificationListener statusUpdateListener() {
		//TODO ×¢²álistenrer
//		return new StatusUpdateGemfireNotificationListener();
//	}

	@Bean(name = "gemfireTransactionManager")
	public GemfireTransactionManager transactionManager(Cache gemfireCache, LocalRegionFactoryBean<String, OrderStatus> localRegionFactoryBean)
			throws Exception {
		GemfireTransactionManager transactionManager = new GemfireTransactionManager();
		transactionManager.setCache(gemfireCache);
		transactionManager.setRegion(localRegionFactoryBean.getObject());
		return transactionManager;
	}

	//
	// @Bean
	// CacheServerFactoryBean cacheServerFactoryBean(Cache gemfireCache) throws
	// Exception {
	// CacheServerFactoryBean cacheServerFactoryBean = new
	// CacheServerFactoryBean();
	// cacheServerFactoryBean.setBindAddress("127.0.0.1");
	// cacheServerFactoryBean.setPort(40404);
	// cacheServerFactoryBean.setCache(gemfireCache);
	// return cacheServerFactoryBean;
	// }
	//

	// @Bean
	// PoolFactoryBean poolFactoryBean() {
	// ArrayList<InetSocketAddress> servers = new
	// ArrayList<InetSocketAddress>();
	// servers.add(new InetSocketAddress("127.0.0.1", 40404));
	// PoolFactoryBean poolFactoryBean = new PoolFactoryBean();
	// poolFactoryBean.setServers(servers);
	// poolFactoryBean.setSubscriptionEnabled(Boolean.TRUE);
	// return poolFactoryBean;
	// }
	//
	// @Bean
	// ClientCacheFactoryBean clientCacheFactoryBean(Pool pool) throws Exception
	// {
	// ClientCacheFactoryBean clientCacheFactoryBean = new
	// ClientCacheFactoryBean();
	// clientCacheFactoryBean.setUseBeanFactoryLocator(false);
	// clientCacheFactoryBean.setPool(pool);
	// return clientCacheFactoryBean;
	// }

}