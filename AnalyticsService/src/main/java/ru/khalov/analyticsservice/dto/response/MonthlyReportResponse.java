package ru.khalov.analyticsservice.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Отчёт за период (по умолчанию — последний месяц).
 */
public record MonthlyReportResponse(
        Instant from,
        Instant to,
        long orderCount,
        long itemCount,
        BigDecimal totalRevenue
) {
}
