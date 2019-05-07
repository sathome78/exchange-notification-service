package me.exrates.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig {

    public static final String EMAIL_INFO_QUEUE = "email-info-queue";
    public static final String EMAIL_MANDRILL_QUEUE = "email-mandrill-queue";
    public static final String EMAIL_QUEUE = "email-queue";
    public static final String EMAIL_LISTING_REQUEST_QUEUE = "email-listing-email-queue";

    private static final Logger logger = LogManager.getLogger(RabbitConfig.class);

    @Bean
    Queue emailInfoQueue() {
        return QueueBuilder.durable(EMAIL_INFO_QUEUE).build();
    }

    @Bean
    Queue emailMandrillQueue() {
        return QueueBuilder.durable(EMAIL_MANDRILL_QUEUE).build();
    }

    @Bean
    Queue emailListingRequestQueue() {
        return QueueBuilder.durable(EMAIL_LISTING_REQUEST_QUEUE).build();
    }

    @Bean
    Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE).build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
