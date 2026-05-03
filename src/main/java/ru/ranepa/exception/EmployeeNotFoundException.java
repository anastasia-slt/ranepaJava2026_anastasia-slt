package ru.ranepa.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Сотрудник с ID " + id + " не найден");
    }
}