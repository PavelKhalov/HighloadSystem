package ru.khalov.cardsrvice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khalov.cardsrvice.entity.Card;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);

    Optional<Card> findByUserId(Long userId);
}
