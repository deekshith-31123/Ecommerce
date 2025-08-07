package com.elasticsearch.demo.controlller;

import com.elasticsearch.demo.model.SellerProduct;
import com.elasticsearch.demo.service.Impl.SellerProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin

@RestController
@RequestMapping("/elasticsearch")
public class Controller {

    @Autowired
    private SellerProductServiceImpl sellerProductService;

    @PostMapping("/add")
    public ResponseEntity<String> indexSellerProduct(@RequestBody SellerProduct product) {
        sellerProductService.addProduct(product);
        return ResponseEntity.ok("Product added in Elastic Search");
    }

    @GetMapping("/search")
    public List<SellerProduct> search(@RequestParam String name) {
        return sellerProductService.searchByName(name);
    }

}
