package com.example.finance.services;

import com.example.finance.DTO.IncomeDTO;
import com.example.finance.entity.Income;
import com.example.finance.entity.User;
import com.example.finance.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    @Override
    public Income postIncome(IncomeDTO incomeDTO, User user) {
        Income income = new Income();
        income.setTitle(incomeDTO.getTitle());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());
        income.setCategory(incomeDTO.getCategory());
        income.setDescription(incomeDTO.getDescription());
        income.setUser(user); // Associate income with the user
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncome(User user) {
        return incomeRepository.findByUser(user).stream()
                .sorted(Comparator.comparing(Income::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Income getIncomeById(Long id, User user) {
        return incomeRepository.findById(id)
                .filter(income -> income.getUser().equals(user)) // Ensure the income belongs to the user
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id " + id));
    }

    @Override
    public Income updateIncome(Long id, IncomeDTO incomeDTO, User user) {
        Income income = getIncomeById(id, user); // Check if income belongs to the user
        income.setTitle(incomeDTO.getTitle());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());
        income.setCategory(incomeDTO.getCategory());
        income.setDescription(incomeDTO.getDescription());
        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id, User user) {
        Income income = getIncomeById(id, user); // Check if income belongs to the user
        incomeRepository.delete(income);
    }
}
