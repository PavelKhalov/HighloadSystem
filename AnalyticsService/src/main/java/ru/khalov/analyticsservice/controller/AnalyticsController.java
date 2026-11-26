package ru.khalov.analyticsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khalov.analyticsservice.dto.response.MonthlyReportResponse;
import ru.khalov.analyticsservice.service.AnalyticsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/report/monthly")
    public ResponseEntity<MonthlyReportResponse> monthlyReport(){
        return ResponseEntity.ok().body(analyticsService.getLastMonthReport());
    }
}
