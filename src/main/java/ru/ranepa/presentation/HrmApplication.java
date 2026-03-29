package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HrmApplication {

    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
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
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showAllEmployees(repository);
                    case 2 -> addEmployee(scanner, repository);
                    case 3 -> deleteEmployee(scanner, repository);
                    case 4 -> findEmployeeById(scanner, repository);
                    case 5 -> findByPosition(scanner, service);
                    case 6 -> showStatistics(service);
                    case 7 -> sortByName(service);
                    case 8 -> sortByHireDate(service);
                    case 9 -> {
                        repository.saveToFile("employees.csv");
                        System.out.println("Exiting HRM System");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose 1-6.");
                }

            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
                scanner.nextLine();
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void showAllEmployees(EmployeeRepository repository) {
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

    private static void addEmployee(Scanner scanner, EmployeeRepository repository) {
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

        Employee newEmployee = new Employee(name, position, salary, hireDate);
        String result = repository.save(newEmployee);

        System.out.println(result);
    }

    private static void deleteEmployee(Scanner scanner, EmployeeRepository repository) {
        System.out.print("Enter employee ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());

        String result = repository.delete(id);
        System.out.println(result);
    }

    private static void findEmployeeById(Scanner scanner, EmployeeRepository repository) {
        System.out.print("Enter employee ID to find: ");
        Long id = Long.parseLong(scanner.nextLine());

        var employee = repository.findById(id);
        if (employee.isPresent()) {
            System.out.println("Found: " + employee.get());
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }
    private static void findByPosition(Scanner scanner, EmployeeService service) {
        System.out.print("\nEnter employee position to find: ");
        String position = scanner.nextLine();
        List<Employee> filtred = service.findByPosition(position);

        System.out.println("\nEmployees with position " + position + ":");
        if (filtred.isEmpty()) {
            System.out.println("No employees found");
        } else {
            for (Employee emp : filtred) {
                System.out.println(emp);
            }
        }
    }
    private static void showStatistics(EmployeeService service) {
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
    private static void sortByName(EmployeeService service) {
        System.out.println("\nEmployees sorted by name:");
        Iterable<Employee> sorted = service.sortByNames();
        for (Employee emp : sorted) {
            System.out.println(emp);
        }
    }
    private static void sortByHireDate(EmployeeService service) {
        System.out.println("\nEmployees sorted by hire date:");
        Iterable<Employee> sorted = service.sortByHireDate();
        for (Employee emp : sorted) {
            System.out.println(emp);
        }
    }

}