package com.roommanagement.repository.roomexpense;

import com.roommanagement.entity.Room;
import com.roommanagement.entity.RoomExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomExpenseRepository extends JpaRepository<RoomExpense, Integer> {
  List<RoomExpense> findAllByExpenseId(Integer expenseId);
  Optional<RoomExpense> findByRoomIdAndExpenseId(Integer roomId, Integer expenseId);
  List<RoomExpense> findAllByRoomIn(List<Room> rooms);
  List<RoomExpense> findAllByRoom(Room rooms);
  List<RoomExpense> findAllByRoomIdIn(Set<Integer> roomIds);
}
