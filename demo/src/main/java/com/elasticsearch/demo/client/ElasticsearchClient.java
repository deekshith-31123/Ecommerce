package com.elasticsearch.demo.client;


import com.elasticsearch.demo.dto.SellerProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "elasticsearch-service", url = "http://localhost:8081")
public interface ElasticsearchClient {

    @PostMapping("/elasticsearch/add")
    ResponseEntity<String> indexSellerProduct(@RequestBody SellerProductDto dto);
}

