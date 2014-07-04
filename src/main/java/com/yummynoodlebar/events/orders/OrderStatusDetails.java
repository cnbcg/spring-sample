package com.yummynoodlebar.events.orders;

import java.util.Date;
import java.util.UUID;

public class OrderStatusDetails {

	private UUID orderId;
	private UUID id;
	private String name;
	private Date statusDate;
	private String status;

	public OrderStatusDetails(UUID orderId, UUID id, String name, Date statusDate, String status) {
		this.orderId = orderId;
		this.id = id;
		this.name = name;
		this.status = status;
		this.statusDate = statusDate;
	}

	public OrderStatusDetails() {
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

	public String getName() {
		return name;
	}
}
