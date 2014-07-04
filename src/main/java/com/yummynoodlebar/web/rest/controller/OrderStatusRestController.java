package com.yummynoodlebar.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yummynoodlebar.events.orders.OrderStatusDetails;
import com.yummynoodlebar.web.controller.OrderStatusController;
import com.yummynoodlebar.web.rest.domain.OrderStatus;

@Controller
@RequestMapping("/aggregators/orders/{orderId}/status")
public class OrderStatusRestController {

	@Autowired
	private OrderStatusController orderStatusController;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<OrderStatus> getOrdderStatus(@PathVariable String orderId) {
		OrderStatusDetails orderStatusDetails = orderStatusController.getOrderStatus(orderId).toOrderStatusDetails();

		if(orderStatusDetails == null) {
			return new ResponseEntity<OrderStatus>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrderStatus>(OrderStatus.fromOrderStatusDetails(orderStatusDetails),
				HttpStatus.OK);
	}
}
