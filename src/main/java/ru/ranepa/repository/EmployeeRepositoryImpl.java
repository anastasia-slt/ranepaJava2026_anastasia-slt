package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<Long, Employee> employees  = new HashMap<>();
    private static Long nextId = 1L;

    @Override
    public String save(Employee employee) {
        employee.setId(nextId++);
        employees.put(employee.getId(), employee);
        return "Employee " + employee.getId() + " was saved successfully";
    }

    @Override
    public Optional<Employee> findById(Long id) {
        // ofNullable безопасно обрабатывает null (если сотрудник не найден)
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    }

    @Override
    public String delete(Long id) {
        Employee removed = employees.remove(id);
        if (removed != null) {
            return "Employee " + id + " was deleted successfully";
        } else {
            return "Employee " + id + " not found";
        }
    }
    // Сохранение в CSV файл
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Заголовок
            writer.println("ID,Name,Position,Salary,HireDate");

            // Данные
            for (Employee employee : employees.values()) {
                writer.printf("%d,%s,%s,%s,%s%n",
                        employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getSalary(),
                        employee.getHireDate()
                );
            }

            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
