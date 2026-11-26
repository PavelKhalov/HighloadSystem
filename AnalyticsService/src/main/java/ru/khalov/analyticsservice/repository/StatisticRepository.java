package ru.khalov.analyticsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khalov.analyticsservice.entity.Statistic;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    boolean existsByEventId(UUID eventId);

    List<Statistic> findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThan(Instant from, Instant to);

    @Query("select coalesce(sum(s.itemCount), 0) from Statistic s " +
            "where s.createdAt >= :from and s.createdAt < :to")
    long sumItemCountBetween(@Param("from") Instant from, @Param("to") Instant to);

    @Query("select coalesce(sum(s.totalPrice), 0) from Statistic s " +
            "where s.createdAt >= :from and s.createdAt < :to")
    java.math.BigDecimal sumTotalPriceBetween(@Param("from") Instant from, @Param("to") Instant to);

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(Instant from, Instant to);
}
