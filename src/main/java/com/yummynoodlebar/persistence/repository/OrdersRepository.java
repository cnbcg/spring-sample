package com.yummynoodlebar.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.yummynoodlebar.persistence.domain.Order;

public interface OrdersRepository extends CrudRepository<Order, UUID> {

	@Query(value = "select no.* from NOODLE_ORDERS no where no.ORDER_KEY in (select ID from ORDER_ORDER_ITEMS where MENU_ID = :menuId)", nativeQuery = true)
	List<Order> findOrdersContaining(@Param("menuId") UUID menuId);
}