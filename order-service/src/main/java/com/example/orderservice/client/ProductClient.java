package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderservice.dto.ProductDto;

@FeignClient(name="product-service")
public interface ProductClient {
	
	@GetMapping("/products/{id}")
	ProductDto getProductById(@PathVariable("id") Integer id);
}
