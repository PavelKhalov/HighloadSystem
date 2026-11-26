package ru.khalov.analyticsservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "statistic")
@Getter
@Setter
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true)
    private UUID eventId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "item_count", nullable = false)
    private Integer itemCount;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    /** когда заказ был создан */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Statistic(){}

    public Statistic(UUID eventId, Long orderId, Long buyerId,
                     Integer itemCount, BigDecimal totalPrice,
                     String status, Instant createdAt){
        this.eventId = eventId;
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }
}
