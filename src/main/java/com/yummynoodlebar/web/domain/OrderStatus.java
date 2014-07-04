package com.yummynoodlebar.web.domain;

import java.util.Date;
import java.util.UUID;

import com.yummynoodlebar.events.orders.OrderStatusDetails;

public class OrderStatus {

	private UUID orderId;
	private UUID id;
	private String name;
	private Date statusDate;
	private String status;

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public OrderStatusDetails toOrderStatusDetails() {
		OrderStatusDetails orderStatusDetails = new OrderStatusDetails(orderId, id, name, statusDate, status);
		return orderStatusDetails;
	}

}
