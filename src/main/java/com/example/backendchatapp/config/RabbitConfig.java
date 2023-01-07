package com.example.backendchatapp.config;

import com.example.backendchatapp.service.Delegator;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public CachingConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

	@Bean
	public RabbitAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		var t = new RabbitTemplate(connectionFactory());
		t.setMessageConverter(converter());
		return t;
	}

	@Bean
	public DirectMessageListenerContainer directMessageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
		var dmlc = new DirectMessageListenerContainer(connectionFactory);
		dmlc.setMessageListener(listenerAdapter);
		return dmlc;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(Delegator delegator){
		 var m = new MessageListenerAdapter();
		 m.setDelegate(delegator);
		 m.setDefaultListenerMethod("receiver");
		 m.setMessageConverter(converter());
		 return m ;
	}
	@Bean
	public Jackson2JsonMessageConverter converter(){
		var j = new  Jackson2JsonMessageConverter();
		j.setCreateMessageIds(true);
		return j;
	}

}
