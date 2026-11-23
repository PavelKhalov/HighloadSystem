package ru.khalov.cardsrvice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "number", nullable = false,  unique = true)
    private String number;

    @Column(name = "last_four_digits", nullable = false, length = 4)
    private String lastFourDigits;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "balance", nullable = false, precision = 9, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Card() {};

    public Card(String number, String lastFourDigits, Long userId){
        this.number = number;
        this.lastFourDigits = lastFourDigits;
        this.userId = userId;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
    }
}