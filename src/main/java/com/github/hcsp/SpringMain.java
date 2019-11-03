package com.github.hcsp;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:config.xml");

        OrderService orderService = (OrderService) beanFactory.getBean("orderService");
        System.out.println(orderService);

        orderService.doSomething();
    }
}
