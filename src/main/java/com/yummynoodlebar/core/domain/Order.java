package com.yummynoodlebar.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.yummynoodlebar.events.orders.OrderDetails;

public class Order {

	private final Date dateTimeOfSubmission;
	private Map<UUID, Integer> orderItems;
	private final UUID key;
	private Customer customer;
	private boolean canBeDeleted = Boolean.TRUE;

	private OrderStatus orderStatus;
	private List<OrderStatus> statusHistory;

	public Order(final Date dateTimeOfSubmission) {
		this.key = UUID.randomUUID();
		this.dateTimeOfSubmission = dateTimeOfSubmission;
		statusHistory = new ArrayList<OrderStatus>();
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		statusHistory.add(orderStatus);
		this.orderStatus = orderStatus;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public Date getDateTimeOfSubmission() {
		return dateTimeOfSubmission;
	}

	public UUID getKey() {
		return key;
	}

	public void setOrderItems(Map<UUID, Integer> orderItems) {
		if (orderItems == null) {
			this.orderItems = Collections.emptyMap();
		} else {
			this.orderItems = Collections.unmodifiableMap(orderItems);
		}
	}

	public Map<UUID, Integer> getOrderItems() {
		return orderItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isCanBeDeleted() {
		return canBeDeleted;
	}

	public Order setCanBeDeleted(boolean canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
		return this;
	}

	public OrderDetails toOrderDetails() {
		OrderDetails details = new OrderDetails();

		BeanUtils.copyProperties(this, details);

		return details;
	}

	public static Order fromOrderDetails(OrderDetails orderDetails) {
		Order order = new Order(orderDetails.getDateTimeOfSubmission());

		BeanUtils.copyProperties(orderDetails, order);

		return order;
	}
}
