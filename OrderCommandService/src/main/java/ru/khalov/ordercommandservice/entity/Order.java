package ru.khalov.ordercommandservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "basket")
    private List<Integer> basket = new ArrayList<>();

    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "created_at")
    private Instant createdAt;

    public Order(){}

    public Order(Long userId){
        this.userId = userId;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
    }
}
