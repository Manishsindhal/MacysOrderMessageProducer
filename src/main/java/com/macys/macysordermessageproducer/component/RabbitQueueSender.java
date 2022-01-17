package com.macys.macysordermessageproducer.component;

import com.macys.macysordermessageproducer.dto.json.OrderJsonMessage;
import com.macys.macysordermessageproducer.dto.xml.FulfillmentOrder;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitQueueSender {
	
	@Autowired
    AmqpTemplate xmlAmqpTemplate;
	
	@Autowired
    AmqpTemplate jsonAmqpTemplate;

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue jsonQueue;

    @Autowired
    private Queue xmlQueue;

    public void send(FulfillmentOrder fulfillmentOrder) throws AmqpException {
//        rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
//        rabbitTemplate.convertAndSend(xmlQueue.getName(), fulfillmentOrder);
    	 jsonAmqpTemplate.convertAndSend(xmlQueue.getName(), fulfillmentOrder);
    }

    public void send(OrderJsonMessage orderMessageJson) throws AmqpException {
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        rabbitTemplate.convertAndSend(jsonQueue.getName(), orderMessageJson);
    	xmlAmqpTemplate.convertAndSend(jsonQueue.getName(), orderMessageJson);
    }
}