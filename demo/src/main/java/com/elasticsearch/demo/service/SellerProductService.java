package com.elasticsearch.demo.service;

import com.elasticsearch.demo.model.SellerProduct;

import java.util.List;

public interface SellerProductService {
    public void addProduct(SellerProduct product);
    public List<SellerProduct> searchByName(String name);
}
