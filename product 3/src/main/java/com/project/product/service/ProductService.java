package com.project.product.service;

import com.project.product.dto.ProductDto;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto getProductById(int productId);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategoryId(int categoryId);
}