package com.project.seller.service;

import com.project.seller.dto.SellerDto;
import com.project.seller.dto.SellerDtoWithRating;
import com.project.seller.dto.SellerProductDto;
import com.project.seller.entity.Seller;

import java.util.List;

public interface SellerService {
    void updateSellerRating(List<SellerProductDto> sellerProductDto);
    Seller addSeller(SellerDto sellerdto);
    SellerDtoWithRating getSellerById(int sellerId);
     List<Seller> getAllDetails();
}
