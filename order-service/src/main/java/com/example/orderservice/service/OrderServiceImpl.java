package com.example.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductDto;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.repository.OrderRepository;

import feign.FeignException;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
//    private final RestTemplate restTemplate;
//    private final WebClient webClient;
	private final UserClient userClient;
	private final ProductClient productClient;

	public OrderServiceImpl(OrderRepository orderRepository, UserClient userClient, ProductClient productClient) {
		this.orderRepository = orderRepository;
		this.userClient = userClient;
		this.productClient = productClient;
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

		Order existingOrder = orderRepository.findById(id).orElse(null);

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
	 * public UserDto getUserById(Integer userId) { String url =
	 * "http://localhost:8081/users/" + userId; return
	 * restTemplate.getForObject(url, UserDto.class); }
	 **/

	/** This is for webClient **/
	/**
	 * public UserDto getUserById(Integer userId) { return webClient .get()
	 * .uri("http://localhost:8081/users/" + userId) .retrieve()
	 * .bodyToMono(UserDto.class) .block(); }
	 **/

	/** This is for UserClient **/
	public UserDto getUserById(Integer userId) {
		try {
			return userClient.getUserById(userId);
		}
		catch(FeignException.NotFound e) {
			throw new UserNotFoundException(
	                "User not found with ID: " + userId
	        );
		}
	}	

	public ProductDto getProductById(Integer prodId) {
		try {
			return productClient.getProductById(prodId);
		}
		catch(FeignException.NotFound e) {
			throw new ProductNotFoundException(
					"Product not found with ID: " + prodId
			);
		}
	}

//	Combine Product and User Service Response
	public OrderResponse getOrderDetailsById(Integer id) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

		UserDto user = getUserById(order.getUserId());
		ProductDto product = getProductById(order.getProductId());
		OrderResponse response = new OrderResponse();

		response.setOrderId(order.getId());

		response.setUserId(user.getId());
		response.setUserName(user.getName());
		response.setUserEmail(user.getEmail());

		response.setProductId(product.getId());
		response.setProductName(product.getName());
		response.setProductPrice(product.getPrice());

		response.setQuantity(order.getQuantity());
		response.setTotalPrice(order.getTotalPrice());

		return response;
	}
}