package com.elasticsearch.demo.repository;

import com.elasticsearch.demo.model.SellerProduct;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface SellerProductRepository extends ElasticsearchRepository<SellerProduct, String> {
    @Query("""
    {
      "match": {
        "productName": {
          "query": "?0"
        }
      }
    }
    """)
    List<SellerProduct> fullTextSearchByProductName(String name);


    // exact search method try to write here

}
