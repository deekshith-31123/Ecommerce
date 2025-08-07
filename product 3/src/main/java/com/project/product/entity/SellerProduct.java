package com.project.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "seller_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"seller_id", "product_id"})
        }
)
public class SellerProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int sellerId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double productPrice;
    private int productQuantity;
    private double productRating;
    private String productName;
    private int productOrderedCompleted;
}
