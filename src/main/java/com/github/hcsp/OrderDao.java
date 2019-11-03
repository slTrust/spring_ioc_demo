package com.github.hcsp;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderDao {
    @Autowired
    private OrderService orderService;

    public void select(){
        System.out.println("select");
    }
}
