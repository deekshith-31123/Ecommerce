package com.elasticsearch.demo.service.Impl;

import com.elasticsearch.demo.model.SellerProduct;
import com.elasticsearch.demo.repository.SellerProductRepository;
import com.elasticsearch.demo.service.SellerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SellerProductServiceImpl implements SellerProductService
{

    @Autowired
    private SellerProductRepository repository;

    public void addProduct(SellerProduct product) {
        repository.save(product);
    }

    public List<SellerProduct> searchByName(String name) {
        return repository.fullTextSearchByProductName(name);
    }
}
