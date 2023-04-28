package com.roommanagement.repository.expense;

import com.roommanagement.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
  List<Expense> findAllByDefaultFlagIsTrueAndUserId(Long userId);
  List<Expense> findAllByDefaultFlagIsFalseAndUserId(Long userId);
}
