package ru.khalov.analyticsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khalov.analyticsservice.dto.BasketItems;
import ru.khalov.analyticsservice.dto.response.MonthlyReportResponse;
import ru.khalov.analyticsservice.entity.Statistic;
import ru.khalov.analyticsservice.event.OrderCreateEvent;
import ru.khalov.analyticsservice.repository.StatisticRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private static final String TOPIC = "order-topic";

    private final StatisticRepository statisticRepository;

    /**
     * Слушаем топик заказов: каждое событие = одна запись статистики.
     * Идемпотентно по eventId — повторная доставка не создаёт дубль.
     */
    @KafkaListener(topics = TOPIC, containerFactory = "containerFactory")
    @Transactional
    public void onOrderCreated(OrderCreateEvent event){
        if (event == null || event.getEventId() == null) {
            log.warn("Skip malformed order event: {}", event);
            return;
        }

        if (statisticRepository.existsByEventId(event.getEventId())) {
            log.debug("Event {} already processed, skip", event.getEventId());
            return;
        }

        int itemCount = event.getBasket() == null ? 0
                : event.getBasket().stream()
                        .mapToInt(BasketItems::quantity)
                        .sum();

        Statistic statistic = new Statistic(
                event.getEventId(),
                event.getOrderId(),
                event.getUserId(),
                itemCount,
                event.getPrice(),
                event.getStatus(),
                event.getCreatedAt()
        );

        statisticRepository.save(statistic);
        log.info("Recorded statistic for order {}", event.getOrderId());
    }

    /** Отчёт за последние 30 дней. */
    public MonthlyReportResponse getLastMonthReport(){
        Instant to = Instant.now();
        Instant from = to.minus(30, ChronoUnit.DAYS);
        return buildReport(from, to);
    }

    private MonthlyReportResponse buildReport(Instant from, Instant to){
        long orderCount = statisticRepository
                .countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(from, to);

        List<Statistic> rows = statisticRepository
                .findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThan(from, to);

        long itemCount = rows.stream()
                .mapToLong(Statistic::getItemCount)
                .sum();

        BigDecimal totalRevenue = rows.stream()
                .map(Statistic::getTotalPrice)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new MonthlyReportResponse(from, to, orderCount, itemCount, totalRevenue);
    }
}
