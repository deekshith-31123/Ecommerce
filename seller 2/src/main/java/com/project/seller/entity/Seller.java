package com.project.seller.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Seller {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int sellerId;
    private String sellerName;

    @Column(nullable = false, unique = true)
    private String sellerEmail;
    private int SellerRating;
}
