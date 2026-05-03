package ru.ranepa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательно")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Должность обязательна")
    @Column(nullable = false)
    private String position;

    @NotNull(message = "Зарплата обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Зарплата должна быть положительной")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @NotNull(message = "Дата приёма обязательна")
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @PrePersist
    protected void onCreate() {
        if (hireDate == null) {
            hireDate = LocalDate.now();
        }
    }
}
