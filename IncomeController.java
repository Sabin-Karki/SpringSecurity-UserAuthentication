package com.example.finance.controller;

import com.example.finance.DTO.IncomeDTO;
import com.example.finance.entity.Income;
import com.example.finance.entity.User;
import com.example.finance.services.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
@CrossOrigin("*")
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> postIncome(@RequestBody IncomeDTO dto, @AuthenticationPrincipal User user) {
        try {

            System.out.println("User = " + user.getUsername());
            Income createdIncome = incomeService.postIncome(dto, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Income>> getAllIncome(@AuthenticationPrincipal User user) {
        try {
            List<Income> incomes = incomeService.getAllIncome(user);
            return ResponseEntity.ok(incomes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        try {
            Income income = incomeService.getIncomeById(id, user);
            return ResponseEntity.ok(income);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody IncomeDTO dto, @AuthenticationPrincipal User user) {
        try {
            Income updatedIncome = incomeService.updateIncome(id, dto, user);
            return ResponseEntity.ok(updatedIncome);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id, @AuthenticationPrincipal User user) {
        try {
            incomeService.deleteIncome(id, user);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
