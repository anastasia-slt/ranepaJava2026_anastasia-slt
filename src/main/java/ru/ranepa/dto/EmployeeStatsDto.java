package ru.ranepa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStatsDto {
    private Long totalEmployees;
    private BigDecimal averageSalary;
    private EmployeeResponseDto topEarner;
}