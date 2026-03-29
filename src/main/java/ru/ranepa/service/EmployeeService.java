package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    // Конструктор
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Расчет средней зарплаты
    public BigDecimal calculateAverageSalary() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (Employee employee : employees) {
            sum = sum.add(employee.getSalary());
        }

        return sum.divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }

    // Поиск самого высокооплачиваемого сотрудникае
    public Employee findHighestPaidEmployee() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        if (employees.isEmpty()) {
            return null;
        }

        Employee highestPaid = employees.getFirst();
        for (Employee employee : employees) {
            if (employee.getSalary().compareTo(highestPaid.getSalary()) > 0) {
                highestPaid = employee;
            }
        }

        return highestPaid;
    }

    // Фильтрация по должности
    public List<Employee> findByPosition(String position) {
        List<Employee> result = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        for (Employee employee : employees) {
            if (employee.getPosition().equalsIgnoreCase(position)) {
                result.add(employee);
            }
        }

        return result;
    }
    // Сортировка по имени (фамилии)
    public List<Employee> sortByNames() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        employees.sort(Comparator.comparing(Employee::getName));

        return employees;
    }

    // Сортировка по дате приема на работу
    public List<Employee> sortByHireDate() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        employees.sort(Comparator.comparing(Employee::getHireDate));

        return employees;
    }
}