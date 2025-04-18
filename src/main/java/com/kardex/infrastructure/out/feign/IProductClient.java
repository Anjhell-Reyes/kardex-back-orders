package com.kardex.infrastructure.out.feign;

import com.kardex.domain.model.Product;
import com.kardex.infrastructure.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-service", url = "http://localhost:8080", configuration = FeignConfiguration.class)
public interface IProductClient {

    @GetMapping("/products/{productId}")
    Product getProduct(@PathVariable("productId") Long productId);
}
