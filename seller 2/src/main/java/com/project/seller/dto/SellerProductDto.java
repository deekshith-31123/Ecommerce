package com.project.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductDto {
    private int sellerId;
    private int productId;
    private double productPrice;
    private int productQuantity;
    private double productRating;
    private int productOrderedCompleted;
}
