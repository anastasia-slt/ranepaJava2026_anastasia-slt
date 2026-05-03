package ru.ranepa.service;

import ru.ranepa.dto.*;
import ru.ranepa.exception.EmployeeNotFoundException;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeResponseDto create(EmployeeRequestDto request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());

        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);
    }

    public EmployeeResponseDto getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return mapToResponse(employee);
    }

    public List<EmployeeResponseDto> getAll() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    public List<EmployeeResponseDto> getByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeStatsDto getStatistics() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            return EmployeeStatsDto.builder()
                    .totalEmployees(0L)
                    .averageSalary(BigDecimal.ZERO)
                    .topEarner(null)
                    .build();
        }

        BigDecimal avgSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);

        Employee top = employees.stream()
                .max((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()))
                .orElse(null);

        return EmployeeStatsDto.builder()
                .totalEmployees((long) employees.size())
                .averageSalary(avgSalary)
                .topEarner(top != null ? mapToResponse(top) : null)
                .build();
    }

    private EmployeeResponseDto mapToResponse(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .hireDate(employee.getHireDate())
                .build();
    }
}