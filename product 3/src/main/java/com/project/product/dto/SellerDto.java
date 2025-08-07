package com.project.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private int sellerId;
    private String sellerName;
    private String sellerEmail;
    private int SellerRating;
}
