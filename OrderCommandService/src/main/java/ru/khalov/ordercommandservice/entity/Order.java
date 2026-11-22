package ru.khalov.ordercommandservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.khalov.ordercommandservice.dto.BasketItems;
import ru.khalov.ordercommandservice.util.OrderStatus;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "basket", columnDefinition = "jsonb")
    private List<BasketItems> basket = new ArrayList<>();
    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    public Order(){}

    public Order(Long userId, List<BasketItems> basket, BigDecimal price){
        this.userId = userId;
        this.basket = basket;
        this.price = price;
        this.status = OrderStatus.PENDING;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
    }
}
