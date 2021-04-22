package com.raven.orderShipping.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raven.order.model.OrderDetail;
import com.raven.order.model.ServiceResponse;
import com.raven.order.model.Status;
import com.raven.orderShipping.publisher.ProcesserPublisher;

@Service
public class OrderShippingListener {

	private final Logger logger = LoggerFactory.getLogger(OrderShippingListener.class);

	@Autowired
	ProcesserPublisher publisher;

	@KafkaListener(topics = { "shipping" }, groupId = "group_id")
	public void consume(String message) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", message));
		OrderDetail orderDetail = new ObjectMapper().readValue(message, OrderDetail.class);

		publisher.sendMessage(ServiceResponse.builder().orderDetail(orderDetail).responseFrom("shipping")
				.status(Status.COMPLETED).build());
	}
}
