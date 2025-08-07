package com.project.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductDto {

    private int id;
    private int sellerId;
    private int productId;
    private String productName;
    private String productBrand;
    private String productDescription;
    private String productImageUrl;
    private int productCategoryId;
    private double productPrice;
    private int productQuantity;
    private double productRating;
    private int productOrderedCompleted;
}