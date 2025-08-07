package com.project.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDtoWithRating {
    private String sellerName;
    private String sellerEmail;
    private int SellerRating;
}
