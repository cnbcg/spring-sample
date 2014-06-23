package com.yummynoodlebar.core.services;

import com.yummynoodlebar.events.orders.AllOrdersEvent;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.DeleteOrderEvent;
import com.yummynoodlebar.events.orders.OrderCreatedEvent;
import com.yummynoodlebar.events.orders.OrderDeletedEvent;
import com.yummynoodlebar.events.orders.OrderDetailsEvent;
import com.yummynoodlebar.events.orders.OrderStatusEvent;
import com.yummynoodlebar.events.orders.OrderUpdatedEvent;
import com.yummynoodlebar.events.orders.RequestAllOrdersEvent;
import com.yummynoodlebar.events.orders.RequestOrderDetailsEvent;
import com.yummynoodlebar.events.orders.RequestOrderStatusEvent;
import com.yummynoodlebar.events.orders.SetOrderPaymentEvent;

public interface OrderService {

	public AllOrdersEvent requestAllOrders(RequestAllOrdersEvent requestAllCurrentOrdersEvent);

	public OrderDetailsEvent requestOrderDetails(RequestOrderDetailsEvent requestOrderDetailsEvent);

	public OrderStatusEvent requestOrderStatus(RequestOrderStatusEvent requestOrderStatusEvent);

	public OrderCreatedEvent createOrder(CreateOrderEvent event);

	public OrderUpdatedEvent setOrderPayment(SetOrderPaymentEvent setOrderPaymentEvent);

	public OrderDeletedEvent deleteOrder(DeleteOrderEvent deleteOrderEvent);
}
