package ru.ranepa.controller;

import ru.ranepa.dto.*;
import ru.ranepa.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "API для управления сотрудниками")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Получить всех сотрудников")
    public ResponseEntity<List<EmployeeResponseDto>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить сотрудника по ID")
    public ResponseEntity<EmployeeResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Создать сотрудника")
    public ResponseEntity<EmployeeResponseDto> create(
            @RequestBody @Valid EmployeeRequestDto request) {
        EmployeeResponseDto created = employeeService.create(request);
        return ResponseEntity
                .created(URI.create("/api/employees/" + created.getId()))
                .body(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить сотрудника")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/position/{position}")
    @Operation(summary = "Поиск по должности")
    public ResponseEntity<List<EmployeeResponseDto>> getByPosition(
            @PathVariable String position) {
        return ResponseEntity.ok(employeeService.getByPosition(position));
    }

    @GetMapping("/stats")
    @Operation(summary = "Статистика")
    public ResponseEntity<EmployeeStatsDto> getStats() {
        return ResponseEntity.ok(employeeService.getStatistics());
    }
}