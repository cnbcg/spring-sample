package com.yummynoodlebar.core.domain;

import java.util.Date;
import java.util.UUID;

import com.yummynoodlebar.events.orders.OrderStatusDetails;

public class OrderStatus {

	private UUID orderId;
	private UUID id;
	private String name;
	private Date statusDate;
	private String status;

	public OrderStatus(UUID orderId, UUID id, String name, final Date date, final String status) {
		this.orderId = orderId;
		this.id = id;
		this.name = name;
		this.status = status;
		this.statusDate = date;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public String getStatus() {
		return status;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public UUID getId() {
		return id;
	}

	public OrderStatusDetails toStatusDetails() {
		return new OrderStatusDetails(orderId, id, name, statusDate, status);
	}

	public static OrderStatus fromStatusDetails(OrderStatusDetails orderStatusDetails) {
		return new OrderStatus(orderStatusDetails.getOrderId(), orderStatusDetails.getId(), orderStatusDetails.getName(), orderStatusDetails.getStatusDate(),
				orderStatusDetails.getStatus());
	}
}
