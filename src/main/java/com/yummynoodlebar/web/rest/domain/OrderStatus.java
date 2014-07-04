package com.yummynoodlebar.web.rest.domain;

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
	private UUID id;

	@XmlElement
	private String name;

	@XmlElement
	private Date statusDate;

	@XmlElement
	private String status;

	public static OrderStatus fromOrderStatusDetails(OrderStatusDetails orderDetails) {
		OrderStatus status = new OrderStatus();

		status.orderId = orderDetails.getOrderId();
		status.id = orderDetails.getId();
		status.name = orderDetails.getName();
		status.statusDate = orderDetails.getStatusDate();
		status.status = orderDetails.getStatus();

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

	public Date getStatusDate() {
		return statusDate;
	}
	
	public String getStatus() {
		return status;
	}

}
