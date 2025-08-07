package com.project.product.controller;

import com.project.product.dto.ProductDto;
import com.project.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/getProductById")
    public ResponseEntity<ProductDto> getProductById(@RequestParam int productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getProductsByCategory")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@RequestParam int categoryId) {
        List<ProductDto> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }
}
