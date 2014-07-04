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

	@Id
	@Column(name = "ORDER_KEY")
	private UUID key;
	
	@Column(name = "SUBMISSION_DATETIME")
	private Date dateTimeOfSubmission;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = java.lang.Integer.class)
	@JoinTable(name = "ORDER_ORDER_ITEMS", joinColumns = @JoinColumn(name = "ID"))
	@MapKeyColumn(name = "MENU_ID")
	@Column(name = "VALUE")
	private Map<UUID, Integer> orderItems;

	private boolean canBeDeleted = Boolean.TRUE;

	@Transient
	private OrderStatus orderStatus;
	
	private String name;
	private String address1;
	private String postcode;
	
	public Order() {
		// required for jpa
	}
	
	public Order(final Date dateTimeOfSubmission) {
		this.key = UUID.randomUUID();
		this.dateTimeOfSubmission = dateTimeOfSubmission;
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

	public boolean isCanBeDeleted() {
		return canBeDeleted;
	}

	public Order setCanBeDeleted(boolean canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
		return this;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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