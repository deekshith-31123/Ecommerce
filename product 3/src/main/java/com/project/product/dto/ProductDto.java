package com.project.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private int id;
    private String productName;
    private String productBrand;
    private String productDescription;
    private String productImageUrl;
    private int productCategoryId;

}