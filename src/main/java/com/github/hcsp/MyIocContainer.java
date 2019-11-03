package com.github.hcsp;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyIocContainer {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(MyIocContainer.class.getResourceAsStream("/ioc.properties"));

        Map<String,Object> beans = new HashMap<>();

        // bean的初始化
        properties.forEach((beanName,beanClass)->{
            try {
                Class<?> klass = Class.forName((String) beanClass);
                Object beanInstance = klass.getConstructor().newInstance();
                beans.put((String)beanName,beanInstance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // 装配依赖
        beans.forEach((beanName,beanInstance)-> dependencyInject(beanName,beanInstance,beans));

        OrderService orderService = (OrderService) beans.get("orderService");
        OrderDao orderDao = (OrderDao) beans.get("orderDao");
        System.out.println();
        orderService.doSomething();
    }

    private static void dependencyInject(String beanName, Object beanInstance, Map<String, Object> beans) {
        // 把所有的 field扫描一遍
        List<Field> fieldsToBeAutowried = Stream.of(beanInstance.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Autowired.class) != null)
                .collect(Collectors.toList());
        // 过滤带有 @Autowired 注解的过滤出来

        fieldsToBeAutowried.forEach(field -> {
            try {
                String fieldName = field.getName();
                Object dependencyBeanInstance = beans.get(fieldName);
                field.setAccessible(true);
                field.set(beanInstance,dependencyBeanInstance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
