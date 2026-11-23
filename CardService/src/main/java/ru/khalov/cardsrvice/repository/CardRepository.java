package ru.khalov.cardsrvice.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khalov.cardsrvice.entity.Card;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);

    Optional<Card> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Card c where c.userId = :userId")
    Optional<Card> findByUserIdForUpdate(@Param("userId") Long userId);
}
