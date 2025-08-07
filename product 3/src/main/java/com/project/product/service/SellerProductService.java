package com.project.product.service;

import com.project.product.dto.SellerPdpDto;
import com.project.product.dto.SellerProductDto;
import java.util.List;

public interface SellerProductService {
    SellerProductDto addSellerProduct(SellerProductDto dto);
    List<SellerProductDto> getProductsBySellerId(int sellerId);
    List<SellerProductDto> getAllSellerProducts();
    SellerProductDto updateSellerProduct(SellerProductDto dto);
     List<SellerPdpDto> getSellerProductDtosByProductId(int productId);
    void purchaseProduct(int productId, int sellerId, int quantity);
}