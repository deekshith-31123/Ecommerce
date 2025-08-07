package com.project.seller.client;

import com.project.seller.dto.SellerProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductServiceClient {

    @GetMapping("/product/getProductsBySellerId")
    ResponseEntity<List<SellerProductDto>> getSellerProducts(@RequestParam int sellerId);
}
