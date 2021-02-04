package configuration;

import com.github.fridujo.rabbitmq.mock.compatibility.MockConnectionFactoryFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Project Name:medical-market;<br/>
 * File Name:MockRabbitConfig;<br/>
 * Package Name:configuration;<br/>
 * Date: 2021-02-01 12:36;<br/>
 * Copyright (c) 2021, www.sq580.com All Rights Reserved.;<br/>
 */
@TestConfiguration
public class MockRabbitConfig {

    @Bean
    ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(MockConnectionFactoryFactory.build());
    }

    @Bean
    public TopicExchange exchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange("exchange").durable(true).build();
    }

    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    @Bean
    public Binding bindBuildersRouteKey1(@Qualifier("exchange") TopicExchange exchange, @Qualifier("queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("*");
    }

}
