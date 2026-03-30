package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HrmApplication {

    // Поля класса (вместо локальных переменных в main)
    private final EmployeeRepository repository;
    private final EmployeeService service;
    private final Scanner scanner;

    public HrmApplication() {
        this.repository = new EmployeeRepositoryImpl();
        this.service = new EmployeeService(repository);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        HrmApplication app = new HrmApplication();
        app.run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showAllEmployees();
                    case 2 -> addEmployee();
                    case 3 -> deleteEmployee();
                    case 4 -> findEmployeeById();
                    case 5 -> findByPosition();
                    case 6 -> showStatistics();
                    case 7 -> sortByName();
                    case 8 -> sortByHireDate();
                    case 9 -> {
                        repository.saveToFile("employees.csv");
                        System.out.println("Exiting HRM System");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose 1-9.");
                }

            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
                scanner.nextLine();
            }
            System.out.println();
        }

        scanner.close();
    }

    // Вывод меню
    private void printMenu() {
        System.out.println("=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add new employee");
        System.out.println("3. Delete employee by ID");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Find employee by position");
        System.out.println("6. Show statistics");
        System.out.println("7. Sort by name");
        System.out.println("8. Sort by hire date");
        System.out.println("9. Exit");
    }

    // Показать всех сотрудников
    private void showAllEmployees() {
        System.out.println("Employee List:");
        Iterable<Employee> employees = repository.findAll();
        boolean isEmpty = true;

        for (Employee emp : employees) {
            System.out.println(emp);
            isEmpty = false;
        }

        if (isEmpty) {
            System.out.println("No employees found.");
        }
    }

    // Добавить сотрудника (вызывает сервис)
    private void addEmployee() {
        System.out.println("Add New Employee:");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        System.out.print("Enter salary: ");
        double salary = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter hire date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate hireDate = LocalDate.parse(dateStr);

        String result = service.addEmployee(name, position, salary, hireDate);
        System.out.println(result);
    }

    // Удалить сотрудника
    private void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());

        String result = service.deleteEmployee(id);
        System.out.println(result);
    }

    // Поиск по ID
    private void findEmployeeById() {
        System.out.print("Enter employee ID to find: ");
        Long id = Long.parseLong(scanner.nextLine());

        var employee = repository.findById(id);
        if (employee.isPresent()) {
            System.out.println("Found: " + employee.get());
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    // Фильтрация по должности
    private void findByPosition() {
        System.out.print("\nEnter employee position to find: ");
        String position = scanner.nextLine();
        List<Employee> filtered = service.findByPosition(position);

        System.out.println("\nEmployees with position '" + position + "':");
        if (filtered.isEmpty()) {
            System.out.println("No employees found");
        } else {
            for (Employee emp : filtered) {
                System.out.println(emp);
            }
        }
    }

    // Показать статистику
    private void showStatistics() {
        System.out.println("Statistics:");

        BigDecimal average = service.calculateAverageSalary();
        System.out.println("Average salary: " + average);

        Employee top = service.findHighestPaidEmployee();
        if (top != null) {
            System.out.println("Top earner: " + top.getName() +
                    " (" + top.getPosition() + ") - " + top.getSalary());
        } else {
            System.out.println("No employees to analyze.");
        }
    }

    // Сортировка по имени
    private void sortByName() {
        System.out.println("\nEmployees sorted by name:");
        List<Employee> sorted = service.sortByNames();
        for (Employee emp : sorted) {
            System.out.println(emp);
        }
    }

    // Сортировка по дате приёма
    private void sortByHireDate() {
        System.out.println("\nEmployees sorted by hire date:");
        List<Employee> sorted = service.sortByHireDate();
        for (Employee emp : sorted) {
            System.out.println(emp);
        }
    }
}