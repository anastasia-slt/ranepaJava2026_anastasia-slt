package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ranepa.dto.EmployeeRequestDto;
import ru.ranepa.dto.EmployeeResponseDto;
import ru.ranepa.dto.EmployeeStatsDto;
import ru.ranepa.exception.EmployeeNotFoundException;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    void shouldCreateEmployee() {
        EmployeeRequestDto request = EmployeeRequestDto.builder()
                .name("Иван Иванов")
                .position("Java Developer")
                .salary(new BigDecimal("150000.00"))
                .hireDate(LocalDate.of(2024, 1, 15))
                .build();

        EmployeeResponseDto result = employeeService.create(request);

        assertNotNull(result.getId());
        assertEquals("Иван Иванов", result.getName());
        assertEquals("Java Developer", result.getPosition());
    }

    @Test
    void shouldCalculateAverageSalary() {
        employeeRepository.save(Employee.builder()
                .name("Employee 1").position("Dev").salary(new BigDecimal("100000"))
                .hireDate(LocalDate.now()).build());
        employeeRepository.save(Employee.builder()
                .name("Employee 2").position("Dev").salary(new BigDecimal("200000"))
                .hireDate(LocalDate.now()).build());
        employeeRepository.save(Employee.builder()
                .name("Employee 3").position("Dev").salary(new BigDecimal("300000"))
                .hireDate(LocalDate.now()).build());

        EmployeeStatsDto stats = employeeService.getStatistics();

        assertEquals(new BigDecimal("200000.00"), stats.getAverageSalary());
        assertEquals(3L, stats.getTotalEmployees());
    }

    @Test
    void shouldFindTopEarner() {
        employeeRepository.save(Employee.builder()
                .name("Low Salary").position("Dev").salary(new BigDecimal("50000"))
                .hireDate(LocalDate.now()).build());
        employeeRepository.save(Employee.builder()
                .name("High Salary").position("Dev").salary(new BigDecimal("200000"))
                .hireDate(LocalDate.now()).build());

        EmployeeStatsDto stats = employeeService.getStatistics();

        assertNotNull(stats.getTopEarner());
        assertEquals("High Salary", stats.getTopEarner().getName());
        assertEquals(new BigDecimal("200000"), stats.getTopEarner().getSalary());
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getById(999L);
        });
    }

    @Test
    void shouldReturnEmptyStatsWhenNoEmployees() {
        EmployeeStatsDto stats = employeeService.getStatistics();

        assertEquals(0L, stats.getTotalEmployees());
        assertEquals(BigDecimal.ZERO, stats.getAverageSalary());
        assertNull(stats.getTopEarner());
    }
}