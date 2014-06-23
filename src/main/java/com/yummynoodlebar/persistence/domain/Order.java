package com.yummynoodlebar.persistence.domain;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Transient;

import org.springframework.beans.BeanUtils;

import com.yummynoodlebar.events.orders.OrderDetails;

@Entity(name = "NOODLE_ORDERS")
public class Order {

	@Column(name = "SUBMISSION_DATETIME")
	private Date dateTimeOfSubmission;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = java.lang.Integer.class)
	@JoinTable(name = "ORDER_ORDER_ITEMS", joinColumns = @JoinColumn(name = "ID"))
	@MapKeyColumn(name = "MENU_ID")
	@Column(name = "VALUE")
	private Map<String, Integer> orderItems;

	private boolean canBeDeleted = Boolean.TRUE;

	@Transient
	private OrderStatus orderStatus;

	@Id
	@Column(name = "ORDER_KEY")
	private UUID key;

	public void setKey(UUID key) {
		this.key = key;
	}

	public void setDateTimeOfSubmission(Date dateTimeOfSubmission) {
		this.dateTimeOfSubmission = dateTimeOfSubmission;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
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

	public void setOrderItems(Map<String, Integer> orderItems) {
		if (orderItems == null) {
			this.orderItems = Collections.emptyMap();
		} else {
			this.orderItems = Collections.unmodifiableMap(orderItems);
		}
	}

	public Map<String, Integer> getOrderItems() {
		return orderItems;
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
		Order order = new Order();

		BeanUtils.copyProperties(orderDetails, order);

		return order;
	}
}