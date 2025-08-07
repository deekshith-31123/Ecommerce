package com.project.product.service.Impl;

import com.project.product.client.ClientSellerService;
import com.project.product.client.ElasticsearchClient;
import com.project.product.dto.SellerDto;
import com.project.product.dto.SellerPdpDto;
import com.project.product.dto.SellerProductDto;
import com.project.product.entity.Product;
import com.project.product.entity.SellerProduct;
import com.project.product.repository.ProductRepository;
import com.project.product.repository.SellerProductRepository;
import com.project.product.service.SellerProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class SellerProductServiceImpl implements SellerProductService {

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientSellerService clientSellerService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public SellerProductDto addSellerProduct(SellerProductDto sellerProductDto) {
        Product product = productRepository.findById(sellerProductDto.getProductId())
                .orElseThrow(() -> {
                    log.error("Product ID {} not found while assigning to seller", sellerProductDto.getProductId());
                    return new RuntimeException("Product not found");
                });

        SellerProduct sp = new SellerProduct();
        BeanUtils.copyProperties(sellerProductDto, sp, "id", "product");
        sp.setProduct(product);
        SellerProduct saved = sellerProductRepository.save(sp);
        log.info("SellerProduct created: sellerId={}, productId={}", saved.getSellerId(), saved.getProduct().getId());
        int sellerId = sellerProductDto.getSellerId();
        clientSellerService.updateSellerRating(sellerId);
        SellerProductDto responseDto = new SellerProductDto();
        BeanUtils.copyProperties(saved, responseDto, "product");
        Product p = saved.getProduct();
        responseDto.setProductId(p.getId());
        responseDto.setProductName(p.getProductName());
        responseDto.setProductBrand(p.getProductBrand());
        responseDto.setProductDescription(p.getProductDescription());
        responseDto.setProductImageUrl(p.getProductImageUrl());
        responseDto.setProductCategoryId(p.getCategory().getId());
        SellerProductDto elasticDto = new SellerProductDto(
                saved.getId(),
                saved.getSellerId(),
                p.getId(),
                p.getProductName(),
                p.getProductBrand(),
                p.getProductDescription(),
                p.getProductImageUrl(),
                p.getCategory().getId(),
                saved.getProductPrice(),
                saved.getProductQuantity(),
                saved.getProductRating(),
                saved.getProductOrderedCompleted()
        );
        try {
            elasticsearchClient.indexSellerProduct(elasticDto);
            log.info("Successfully indexed SellerProduct in Elasticsearch");
        } catch (Exception e) {
            log.error("Failed to index product in Elasticsearch: {}", e.getMessage());
        }
        return responseDto;
    }

    @Override
    public List<SellerProductDto> getProductsBySellerId(int sellerId) {
        List<SellerProduct> list = sellerProductRepository.findBySellerId(sellerId);
        List<SellerProductDto> result = new ArrayList<>();
        for (SellerProduct sp : list) {
            SellerProductDto sellerProductDto = new SellerProductDto();
            BeanUtils.copyProperties(sp, sellerProductDto, "product");
            Product p = sp.getProduct();
            sellerProductDto.setProductId(p.getId());
            sellerProductDto.setProductName(p.getProductName());
            sellerProductDto.setProductBrand(p.getProductBrand());
            sellerProductDto.setProductDescription(p.getProductDescription());
            sellerProductDto.setProductImageUrl(p.getProductImageUrl());
            sellerProductDto.setProductCategoryId(p.getCategory().getId());
            result.add(sellerProductDto);
        }
        return result;
    }

    @Override
    public List<SellerProductDto> getAllSellerProducts() {
        List<SellerProduct> list = sellerProductRepository.findAll();
        List<SellerProductDto> result = new ArrayList<>();
        for (SellerProduct sp : list) {
            SellerProductDto dto = new SellerProductDto();
            BeanUtils.copyProperties(sp, dto, "product");
            Product p = sp.getProduct();
            dto.setProductId(p.getId());
            dto.setProductName(p.getProductName());
            dto.setProductBrand(p.getProductBrand());
            dto.setProductDescription(p.getProductDescription());
            dto.setProductImageUrl(p.getProductImageUrl());
            dto.setProductCategoryId(p.getCategory().getId());
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<SellerPdpDto> getSellerProductDtosByProductId(int productId) {
         List<SellerProduct> sellerProducts = sellerProductRepository.findByProductId(productId);
        if (sellerProducts == null || sellerProducts.isEmpty()) {
            log.info("No SellerProduct records found for productId {}", productId);
            return Collections.emptyList();
        }
         List<SellerDto> allSellers = clientSellerService.getAllDetails();

        Map<Integer, Integer> sellerRatingMap = new HashMap<>();
        Map<Integer, SellerDto> sellerDtoMap = new HashMap<>();
        for (SellerDto seller : allSellers) {
            sellerRatingMap.put(seller.getSellerId(), seller.getSellerRating());
            sellerDtoMap.put(seller.getSellerId(), seller);
        }
         List<SellerPdpDto> resultList = new ArrayList<>();
        for (SellerProduct sp : sellerProducts) {
            if (sellerRatingMap.containsKey(sp.getSellerId())) {
                double rating = sellerRatingMap.get(sp.getSellerId());
                SellerPdpDto dto = new SellerPdpDto(
                        sp.getSellerId(),
                        sellerDtoMap.get(sp.getSellerId()).getSellerName(),
                        sp.getProductPrice(),
                        sp.getProductQuantity(),
                        sp.getProductRating(),
                        sp.getProductOrderedCompleted()
                );

                resultList.add(dto);
            }
        }
         Collections.sort(resultList, new Comparator<SellerPdpDto>() {
            @Override
            public int compare(SellerPdpDto o1, SellerPdpDto o2) {
                return Double.compare(o2.getProductRating(), o1.getProductRating());
            }
        });
        log.info("Returning {} SellerPdpDto sorted by sellerRating for productId {}", resultList.size(), productId);
        return resultList;
    }

    @Override
    public SellerProductDto updateSellerProduct(SellerProductDto dto) {
        SellerProduct existing = sellerProductRepository.findById(dto.getId())
                .orElseThrow(() -> {
                    log.error("SellerProduct not found for id {}", dto.getId());
                    return new RuntimeException("SellerProduct not found");
                });

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> {
                    log.error("Product not found for id {}", dto.getProductId());
                    return new RuntimeException("Product not found");
                });
        BeanUtils.copyProperties(dto, existing, "id", "product");
        existing.setProduct(product);
        SellerProduct updated = sellerProductRepository.save(existing);
        log.info("Updated SellerProduct with ID {}", updated.getId());

         try {
            clientSellerService.updateSellerRating(updated.getSellerId());
            log.info("Seller rating updated for sellerId={}", updated.getSellerId());
        } catch (Exception e) {
            log.error("Failed to update seller rating for sellerId={}", updated.getSellerId(), e);
        }
         SellerProductDto response = new SellerProductDto();
        BeanUtils.copyProperties(updated, response, "product");
        response.setProductId(product.getId());
        response.setProductName(product.getProductName());
        response.setProductBrand(product.getProductBrand());
        response.setProductDescription(product.getProductDescription());
        response.setProductImageUrl(product.getProductImageUrl());
        response.setProductCategoryId(product.getCategory().getId());

        return response;
    }

    //tripathy
    @Transactional
    public void purchaseProduct(int productId,int sellerId, int quantity) {
        SellerProduct product = sellerProductRepository.findByIdForPessimisticWrite(productId,sellerId);

        if (product.getProductQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }
        product.setProductQuantity(product.getProductQuantity() - quantity);
        sellerProductRepository.save(product);
        clientSellerService.updateSellerRating(sellerId);
    }

}
