package com.yummynoodlebar.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yummynoodlebar.persistence.domain.OrderStatus;

public interface OrderStatusRepository extends CrudRepository<OrderStatus, UUID> {

	public OrderStatus findLatestById(UUID key);

	@Query("SELECT DISTINCT * FROM /YummyNoodleOrder WHERE orderId = $1 ORDER BY statusDate")
	public Collection<OrderStatus> getOrderHistory(UUID orderId);
}
