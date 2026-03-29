package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new EmployeeRepositoryImpl();
        employeeService = new EmployeeService(repository);
    }

    @Test
    public void shouldCalculateAverageSalary() {
        repository.save(new Employee("Anna", "Developer", 100000.0, LocalDate.now()));
        repository.save(new Employee("Boris", "Manager", 80000.0, LocalDate.now()));
        repository.save(new Employee("Victor", "QA", 90000.0, LocalDate.now()));

        BigDecimal average = employeeService.calculateAverageSalary();

        assertEquals(90000.0, average.doubleValue(), 0.01);
    }

    @Test
    public void shouldFindHighestPaidEmployee() {
        repository.save(new Employee("Anna", "Developer",
                100000.0, LocalDate.now()));
        repository.save(new Employee("Boris", "Manager",
                80000.0, LocalDate.now()));

        Employee topEmployee = employeeService.findHighestPaidEmployee();

        assertNotNull(topEmployee);
        assertEquals("Anna", topEmployee.getName());
        assertEquals(BigDecimal.valueOf(100000.0), topEmployee.getSalary());
    }

    @Test
    public void shouldReturnNullWhenNoEmployees() {
        Employee result = employeeService.findHighestPaidEmployee();
        assertNull(result);
    }
}