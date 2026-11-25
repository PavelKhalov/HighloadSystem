package ru.khalov.catalogqueriesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khalov.catalogqueriesservice.entiry.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.sellerId =:sellerId and p.id =:productId")
    Optional<Product> findProductBySellerIdAndProductId(@Param("productId") Long sellerId, @Param("sellerId") Integer productId);

    @Query("select p from Product p where p.sellerId =:sellerId")
    List<Product> findAllProductBySellerId(@Param("sellerId") Long sellerId);
}
