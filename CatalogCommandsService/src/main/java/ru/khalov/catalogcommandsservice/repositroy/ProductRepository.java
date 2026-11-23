package ru.khalov.catalogcommandsservice.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khalov.catalogcommandsservice.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductBySellerIdAndId(Long sellerId, Integer id);
}
