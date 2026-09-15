package com.example.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
//    private final RestTemplate restTemplate;
//    private final WebClient webClient;
    private final UserClient userClient;

    public OrderServiceImpl(OrderRepository orderRepository,UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Integer id, Order order) {

        Order existingOrder =
                orderRepository.findById(id).orElse(null);

        if (existingOrder == null) {
            return null;
        }

        existingOrder.setUserId(order.getUserId());
        existingOrder.setProductId(order.getProductId());
        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setTotalPrice(order.getTotalPrice());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    /** This is for restTemplate **/
    /**
    public UserDto getUserById(Integer userId) {
    	String url = "http://localhost:8081/users/" + userId;
    	return restTemplate.getForObject(url, UserDto.class);
    }
    **/
    
    /** This is for webClient **/
    /**
    public UserDto getUserById(Integer userId) {
    	return webClient
    			.get()
    			.uri("http://localhost:8081/users/" + userId)
    			.retrieve()
    			.bodyToMono(UserDto.class)
    			.block();
    }
    **/
    
    /** This is for UserClient **/
    public UserDto getUserById(Integer userId) {
    	return userClient.getUserById(userId);
    }
}