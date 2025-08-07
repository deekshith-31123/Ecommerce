package com.project.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productName;
    @Column(length = 2000)
    private String productDescription;
    private String productBrand;
    private String productImageUrl;

    @OneToMany(mappedBy = "product")
    private List<SellerProduct> sellerProducts;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;
}
