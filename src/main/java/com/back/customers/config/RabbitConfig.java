package com.back.customers.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String GET_ALL_CUSTOMERS_QUEUE = "getAllCustomersQueue";
    public static final String GET_CUSTOMER_BY_ID_QUEUE = "getCustomerByIdQueue";
    public static final String CREATE_CUSTOMER_QUEUE = "createCustomerQueue";
    public static final String UPDATE_CUSTOMER_QUEUE = "updateCustomerQueue";

    public static final String DELETE_CUSTOMER_QUEUE = "deleteCustomerQueue";

    @Bean
    public Queue getAllCustomersQueue() {
        return new Queue(GET_ALL_CUSTOMERS_QUEUE);
    }

    @Bean
    public Queue getCustomerByIdQueue() {
        return new Queue(GET_CUSTOMER_BY_ID_QUEUE);
    }

    @Bean
    public Queue createCustomerQueue() {
        return new Queue(CREATE_CUSTOMER_QUEUE);
    }

    @Bean
    public Queue updateCustomerQueue() {
        return new Queue(UPDATE_CUSTOMER_QUEUE);
    }

    @Bean
    public Queue deleteCustomQueue() {
        return new Queue(DELETE_CUSTOMER_QUEUE);
    }
}
