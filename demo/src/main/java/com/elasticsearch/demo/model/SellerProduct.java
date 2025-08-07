package com.elasticsearch.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "sellerproduct")
@Setting(settingPath = "/elasticsearch/seller_product_index_settings.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerProduct {

    @Id
    private String id;

    private int sellerId;
    private int productId;

    @Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer", searchAnalyzer = "standard")
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
