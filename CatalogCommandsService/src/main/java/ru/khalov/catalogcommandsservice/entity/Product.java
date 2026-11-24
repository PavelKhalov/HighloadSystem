package ru.khalov.catalogcommandsservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "seller_id", nullable = false, updatable = false)
    private Long sellerId;

    @Column(name = "price_one_product", nullable = false)
    private BigDecimal priceOneProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at")
    private Instant createdAt;

    public Product (){}

    public Product(String title, Long sellerId, BigDecimal priceOneProduct, Integer quantity){
        this.title = title;
        this.sellerId = sellerId;
        this.priceOneProduct = priceOneProduct;
        this.quantity = quantity;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
    }
}
