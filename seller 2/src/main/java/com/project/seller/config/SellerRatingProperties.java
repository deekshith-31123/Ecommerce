package com.project.seller.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rating")
public class SellerRatingProperties
{
    private Max max = new Max();
    private Weight weight = new Weight();

    @Data
    public static class Max {
        private int products;
        private int quantity;
        private int orders;
        private double price;
        private double rating;
    }

    @Data
    public static class Weight {
        private double products;
        private double quantity;
        private double orders;
        private double price;
        private double rating;
    }
}
