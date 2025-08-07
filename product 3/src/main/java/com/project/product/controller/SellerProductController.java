package com.project.product.controller;

import com.project.product.dto.SellerPdpDto;
import com.project.product.dto.SellerProductDto;
import com.project.product.service.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class SellerProductController {

    @Autowired
    private SellerProductService sellerProductService;

    @PostMapping("/addSellerProduct")
    public ResponseEntity<SellerProductDto> addSellerProduct(@RequestBody SellerProductDto dto) {
        SellerProductDto addedProduct = sellerProductService.addSellerProduct(dto);

        return ResponseEntity.ok(addedProduct);
    }

    @GetMapping("/getProductsBySellerId")
    public ResponseEntity<List<SellerProductDto>> getProductsBySellerId(@RequestParam int sellerId) {
        List<SellerProductDto> products = sellerProductService.getProductsBySellerId(sellerId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getAllSellerProducts")
    public ResponseEntity<List<SellerProductDto>> getAllSellerProducts() {
        List<SellerProductDto> allProducts = sellerProductService.getAllSellerProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/getSellerIdsForProduct")
    public List<SellerPdpDto> getSellerIdsForProduct(@RequestParam Integer productId) {
        return sellerProductService.getSellerProductDtosByProductId(productId);
    }

    @PutMapping("/updateSellerRating")
    public ResponseEntity<SellerProductDto> updateProduct(@RequestBody SellerProductDto dto) {
        SellerProductDto updated = sellerProductService.updateSellerProduct(dto);
        return ResponseEntity.ok(updated);
    }



    @PostMapping("/buy")
    public ResponseEntity<String> buyProduct(
            @RequestParam int productId,@RequestParam int sellerId,
            @RequestParam int quantity) {
        try {
            sellerProductService.purchaseProduct(productId, sellerId,quantity);
            return ResponseEntity.ok("Purchase successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
