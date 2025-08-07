package com.project.product.client;


import com.project.product.dto.SellerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seller-service", url = "http://localhost:8385/")

public interface ClientSellerService {
    @GetMapping("/seller/api/getAllDetails")
    List<SellerDto> getAllDetails();

    @PutMapping("/seller/api/sellerRating")
    void updateSellerRating(@RequestParam int sellerId);
}
