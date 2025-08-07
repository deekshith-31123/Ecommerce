package com.project.product.repository;

import com.project.product.entity.SellerProduct;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerProductRepository extends JpaRepository<SellerProduct, Integer> {
    List<SellerProduct> findBySellerId(int sellerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sp FROM SellerProduct sp WHERE sp.product.id = :pid AND sp.sellerId = :sid")
    SellerProduct findByIdForPessimisticWrite(@Param("pid") int pid, @Param("sid") int sid);

    @Query("SELECT sp.sellerId FROM SellerProduct sp WHERE sp.product.id = :productId")
    List<Integer> findSellerIdsByProductId(@Param("productId") int productId);

    List<SellerProduct> findByProductId(int productId);
}