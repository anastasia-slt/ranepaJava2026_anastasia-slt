package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    //Alt + insert

    public Employee(String name, String position, double salary, LocalDate hireDate) {
        // Проверка: зарплата должна быть больше 0
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be greater than 0. Got: " + salary);
        }

        // Проверка: дата приёма не может быть в будущем
        if (hireDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Hire date cannot be in the future. Got: " + hireDate);
        }

        // Проверка: имя не должно быть пустым
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        // Проверка: должность не должна быть пустой
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be empty");
        }

        // Если все проверки пройдены — присваиваем значения
        this.name = name;
        this.position = position;
        this.salary = BigDecimal.valueOf(salary);
        this.hireDate = hireDate;
    }
    //Alt + insert-> toString
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
