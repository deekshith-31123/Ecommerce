package com.project.product.service.Impl;

import com.project.product.dto.ProductDto;
import com.project.product.entity.Product;
import com.project.product.entity.ProductCategory;
import com.project.product.repository.ProductCategoryRepository;
import com.project.product.repository.ProductRepository;
import com.project.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        ProductCategory category = categoryRepository.findById(productDto.getProductCategoryId())
                .orElseThrow(() -> {
                    log.error("Category ID {} not found", productDto.getProductCategoryId());
                    return new RuntimeException("Category not found");
                });
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product, "productId", "category");
        product.setCategory(category);
        Product saved = productRepository.save(product);
        log.info("Product created with ID {}", saved.getId());
        ProductDto responseDto = new ProductDto();
        BeanUtils.copyProperties(saved, responseDto, "category");
        responseDto.setProductCategoryId(saved.getCategory().getId());
        return responseDto;
    }

    @Override
    public ProductDto getProductById(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product ID {} not found", productId);
                    return new RuntimeException("Product not found");
                });
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto, "category");
        productDto.setProductCategoryId(product.getCategory().getId());
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(product, productDto, "category");
            productDto.setProductCategoryId(product.getCategory().getId());
            result.add(productDto);
        }
        return result;
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(int categoryId) {
        List<Product> products = productRepository.findByCategory_Id(categoryId);
        List<ProductDto> result = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(product, productDto, "category");
            productDto.setProductCategoryId(product.getCategory().getId());
            productDto.setId(product.getId());
            result.add(productDto);
        }
        return result;
    }
}
