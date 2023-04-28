package com.roommanagement.repository.roomexpense;

import com.roommanagement.entity.Room;
import com.roommanagement.entity.RoomExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomExpenseRepository extends JpaRepository<RoomExpense, Integer> {
  List<RoomExpense> findAllByExpenseId(Integer expenseId);
  Optional<RoomExpense> findByRoomIdAndExpenseId(Long roomId, Integer expenseId);
}
