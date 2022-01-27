package ru.itis.javalab.afarvazov.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.javalab.afarvazov.models.Document;

@EnableRabbit
@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbit.exchange}")
    private String exchange;

    @Value("${rabbit.queue.default}")
    private String queueDefault;

    @Bean
    public Queue defaultQueue() {
        return new Queue(queueDefault);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding defaultBinding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("welcome");
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> containerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(10);
        factory.setConcurrentConsumers(5);
        factory.setReceiveTimeout(1000L);

        return factory;
    }

}
