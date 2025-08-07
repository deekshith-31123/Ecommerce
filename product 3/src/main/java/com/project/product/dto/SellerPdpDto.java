package com.project.product.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerPdpDto {

//    private int id;
    private int sellerId;
    private String sellerName;
    private double productPrice;
    private int productQuantity;
    private double productRating;
    private int productOrderedCompleted;
}