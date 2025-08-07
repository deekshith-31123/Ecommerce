package com.project.seller.service.Impl;

import com.project.seller.config.SellerRatingProperties;
import com.project.seller.dto.SellerDtoWithRating;
import com.project.seller.dto.SellerProductDto;
import com.project.seller.client.ProductServiceClient;
import com.project.seller.dto.SellerDto;
import com.project.seller.entity.Seller;
import com.project.seller.repository.SellerRepository;
import com.project.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private final SellerRepository sellerRepository;
    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private SellerRatingProperties ratingProperties;

    @Override
    public void updateSellerRating(List<SellerProductDto> sellerProductDto) {
        if (sellerProductDto == null || sellerProductDto.isEmpty()) {
            return;
        }
        int sellerId = sellerProductDto.get(0).getSellerId();
        int totalProducts = sellerProductDto.size();
        int totalQuantity = sellerProductDto.stream().mapToInt(SellerProductDto::getProductQuantity).sum();
        int totalOrders = sellerProductDto.stream().mapToInt(SellerProductDto::getProductOrderedCompleted).sum();
        double averagePrice = sellerProductDto.stream().mapToDouble(SellerProductDto::getProductPrice).average().orElse(0.0);
        double averageRating = sellerProductDto.stream().mapToDouble(SellerProductDto::getProductRating).average().orElse(0.0);

        int maxProducts = ratingProperties.getMax().getProducts();
        int maxQuantity = ratingProperties.getMax().getQuantity();
        int maxOrders = ratingProperties.getMax().getOrders();
        double maxPrice = ratingProperties.getMax().getPrice();
        double maxRating = ratingProperties.getMax().getRating();
        double weightProducts = ratingProperties.getWeight().getProducts();
        double weightQuantity = ratingProperties.getWeight().getQuantity();
        double weightOrders = ratingProperties.getWeight().getOrders();
        double weightPrice = ratingProperties.getWeight().getPrice();
        double weightRating = ratingProperties.getWeight().getRating();
        double totalWeight = weightProducts + weightQuantity + weightOrders + weightPrice + weightRating;
        double normProducts = Math.min(totalProducts, maxProducts) / (double) maxProducts;
        double normQuantity = Math.min(totalQuantity, maxQuantity) / (double) maxQuantity;
        double normOrders = Math.min(totalOrders, maxOrders) / (double) maxOrders;
        double normPrice = (maxPrice - Math.min(averagePrice, maxPrice)) / maxPrice;
        double normRating = averageRating / maxRating;
        double weightedScore = (
                normProducts * weightProducts +
                        normQuantity * weightQuantity +
                        normOrders * weightOrders +
                        normPrice * weightPrice +
                        normRating * weightRating
        ) / totalWeight;
        int finalRating = (int) Math.round(weightedScore * 10);
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() ->
                new RuntimeException("Seller not found with ID: " + sellerId)
        );
        seller.setSellerRating(finalRating);
        sellerRepository.save(seller);
        System.out.println("Updated seller rating for ID " + sellerId + ": " + finalRating);
    }

    @Override
    public Seller addSeller(SellerDto sellerdto) {
        Seller seller = new Seller();
        BeanUtils.copyProperties(sellerdto, seller);
        log.info("Student created: {}", seller.getSellerId());
        return sellerRepository.save(seller);
    }

    @Override
    public SellerDtoWithRating getSellerById(int sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found: " +sellerId));
        SellerDtoWithRating sellerDtoWithRating = new SellerDtoWithRating();
        BeanUtils.copyProperties(seller,sellerDtoWithRating);
        log.info("Seller By Id: {}", sellerId);
        return sellerDtoWithRating;
    }
    public List<Seller> getAllDetails()
    {
        return sellerRepository.findAll();
    }
}
