package com.yummynoodlebar.rest.domain;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.yummynoodlebar.events.orders.OrderStatusDetails;

@XmlRootElement
public class OrderStatus {

	@XmlElement
	private UUID orderId;

	@XmlElement
	private Date statusDate;

	@XmlElement
	private String status;

	public static OrderStatus fromOrderStatusDetails(UUID key, OrderStatusDetails orderDetails) {
		OrderStatus status = new OrderStatus();

		status.orderId = key;
		status.status = orderDetails.getStatus();
		status.statusDate = orderDetails.getStatusDate();

		return status;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public String getStatus() {
		return status;
	}
}
