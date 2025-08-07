package com.project.product.client;

import com.project.product.dto.SellerProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "elasticsearch-service", url = "http://localhost:8281") // Adjust if needed
public interface ElasticsearchClient {

    @PostMapping("/elasticsearch/add")
    void indexSellerProduct(@RequestBody SellerProductDto dto);
}
