package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    // Конструктор
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Расчет средней зарплаты
    public BigDecimal calculateAverageSalary() {
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;

        for (Employee employee : employeeRepository.findAll()) {
            sum = sum.add(employee.getSalary());
            count++;
        }

        if (count == 0) {
            return BigDecimal.ZERO;
        }

        return sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    // Поиск самого высокооплачиваемого сотрудника
    public Employee findHighestPaidEmployee() {
        Employee highestPaid = null;

        for (Employee employee : employeeRepository.findAll()) {
            if (highestPaid == null ||
                    employee.getSalary().compareTo(highestPaid.getSalary()) > 0) {
                highestPaid = employee;
            }
        }

        return highestPaid;
    }

    // Фильтрация по должности
    public List<Employee> findByPosition(String position) {
        List<Employee> result = new ArrayList<>();

        for (Employee employee : employeeRepository.findAll()) {
            if (employee.getPosition().equalsIgnoreCase(position)) {
                result.add(employee);
            }
        }

        return result;
    }

    // Сортировка по имени
    public List<Employee> sortByNames() {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee);
        }

        employees.sort(Comparator.comparing(Employee::getName));
        return employees;
    }

    // Сортировка по дате приема на работу
    public List<Employee> sortByHireDate() {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()) {
            employees.add(employee);
        }

        employees.sort(Comparator.comparing(Employee::getHireDate));
        return employees;
    }
    // Добавление сотрудника
    public String addEmployee(String name, String position, double salary, LocalDate hireDate) {
        Employee newEmployee = new Employee(name, position, salary, hireDate);
        return employeeRepository.save(newEmployee);
    }
    // Удаление сотрудника
    public String deleteEmployee(Long id) {
        return employeeRepository.delete(id);
    }

}