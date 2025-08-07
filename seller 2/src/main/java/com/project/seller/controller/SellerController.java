package com.project.seller.controller;


import com.project.seller.dto.SellerDto;
import com.project.seller.dto.SellerDtoWithRating;
import com.project.seller.dto.SellerProductDto;
import com.project.seller.entity.Seller;
import com.project.seller.service.SellerService;
import com.project.seller.client.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductServiceClient client;

    @PostMapping("/api/addSeller")
    public ResponseEntity<Boolean> addSeller(@RequestBody SellerDto sellerDto) {
        sellerService.addSeller(sellerDto);
        return ResponseEntity.ok(true);
    }
    @PutMapping("/api/sellerRating")
    public void updateSellerRating(@RequestParam int sellerId) {
        ResponseEntity<List<SellerProductDto>> response = client.getSellerProducts(sellerId);

        List<SellerProductDto> sellerProductList = response.getBody();
        if (sellerProductList != null) {
            sellerService.updateSellerRating(sellerProductList);
        }
    }

    @GetMapping("/api/getSellerById")
    public ResponseEntity<SellerDtoWithRating>  getSellerById(@RequestParam int sellerId){
        SellerDtoWithRating sellerDtoWithRating = sellerService.getSellerById(sellerId);
        return ResponseEntity.ok(sellerDtoWithRating);
    }

    @GetMapping("/api/getAllDetails")
    public List<Seller> getAllDetails()
    {
        List<Seller> sellers=sellerService.getAllDetails();
        return sellers;
    }
}
