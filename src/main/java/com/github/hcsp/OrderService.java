package com.github.hcsp;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
    // 依赖另一个模块
    // @Autowired
    // @Inject
    // @Resource
    // private OrderDao orderDao;

    @Autowired
    private OrderDao orderDao;

    public void doSomething(){
        System.out.println(orderDao);
        orderDao.select();
    }
}
