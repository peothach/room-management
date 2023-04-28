package com.roommanagement.repository.roomexpense;

import com.roommanagement.entity.Room;
import com.roommanagement.entity.RoomExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomExpenseRepository extends JpaRepository<RoomExpense, Integer> {
  List<RoomExpense> findAllByExpenseId(Integer expenseId);
  List<RoomExpense> findAllByRoomId(Long roomId);
}
