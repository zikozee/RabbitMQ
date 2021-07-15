package com.zikozee.rabbit_two.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zikoz
 * @created : 21 Jun, 2021
 */

@Configuration
public class RabbitmqSchemaConfig {
    public static final String EXCHANGE = "x.another-dummy";
    public static final String QUEUE = "q.another-dummy";


    //todo info using declarables to create instead of individual methods below
    @Bean
    public Declarables rabbitmqSchema(){
        return new Declarables(new FanoutExchange(EXCHANGE, true, false),
                new Queue(QUEUE),
                new Binding(QUEUE, Binding.DestinationType.QUEUE, EXCHANGE, "", null));
    }
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return new FanoutExchange(EXCHANGE, true, false);
//    }
//
//    @Bean
//    public Queue queueAnotherDummy(){
//        return new Queue(QUEUE);
//    }
//
//    @Bean
//    public Binding bindingAnotherDummy(){
////        return new Binding(QUEUE, Binding.DestinationType.QUEUE, EXCHANGE, "", null);
//        return BindingBuilder.bind(queueAnotherDummy())
//                .to(fanoutExchange());
//    }


//    @Bean
//    public Binding bindingForDirectExchange(){
////        return new Binding(QUEUE, Binding.DestinationType.QUEUE, EXCHANGE, null, null);
//        return BindingBuilder.bind(queueAnotherDummy())
//                .to(directExchange()).with("routing_key");
//    }
//
//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange(...)
//    }
//
//    @Bean
//    public TopicExchange topicExchange(){
//        return new DirectExchange(...)
//    }
//
//    @Bean
//    public HeadersExchange headersExchange(){
//        return new DirectExchange(...)
//    }
}
