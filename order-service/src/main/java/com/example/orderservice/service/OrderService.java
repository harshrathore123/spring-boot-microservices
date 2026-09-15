package com.example.orderservice.service;

import java.util.List;

import com.example.orderservice.dto.UserDto;
import com.example.orderservice.entity.Order;

public interface OrderService {

    Order createOrder(Order order);

    List<Order> getAllOrders();

    Order getOrderById(Integer id);

    Order updateOrder(Integer id, Order order);

    void deleteOrder(Integer id);
    
    UserDto getUserById(Integer userId);
}