package com.macys.macysordermessageproducer.configuration;

import com.macys.macysordermessageproducer.component.RabbitAMQPConstants;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MACYSProducerConfig {

    @Bean
    Queue jsonQueue() {
        return new Queue(RabbitAMQPConstants.JSON_QUEUE, true, false, false);
    }

    @Bean
    Queue xmlQueue() {
        return new Queue(RabbitAMQPConstants.XML_QUEUE, true, false, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(RabbitAMQPConstants.EXCHANGE, false, false);
    }

    @Bean
    Binding binding1(DirectExchange exchange) {
        return BindingBuilder.bind(jsonQueue()).to(exchange).with(jsonQueue().getName());
    }

    @Bean
    Binding binding2(DirectExchange exchange) {
        return BindingBuilder.bind(xmlQueue()).to(exchange).with(xmlQueue().getName());
    }
    
    @Bean
    public AmqpTemplate jsonAmqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(RabbitAMQPConstants.ROUTING_KEY);
        rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
        rabbitTemplate.setDefaultReceiveQueue(jsonQueue().getName());
        return rabbitTemplate;
    }

    @Bean
    public AmqpTemplate xmlAmqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(RabbitAMQPConstants.ROUTING_KEY);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setDefaultReceiveQueue(xmlQueue().getName());
        return rabbitTemplate;
    }
}