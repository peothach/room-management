package com.roommanagement.service.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;
import com.roommanagement.entity.Expense;
import com.roommanagement.entity.Room;
import com.roommanagement.entity.RoomExpense;
import com.roommanagement.entity.User;
import com.roommanagement.repository.expense.ExpenseRepository;
import com.roommanagement.repository.roomexpense.RoomExpenseRepository;
import com.roommanagement.repository.user.UserRepository;
import com.roommanagement.repository.room.RoomRepository;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.util.UserUtils;
import com.roommanagement.valueoject.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommandRoomServiceImpl implements CommandRoomService {
  private final RoomRepository roomRepository;
  private final RoomExpenseRepository roomExpenseRepository;
  private final ExpenseRepository expenseRepository;
  private final UserUtils userUtils;
  private final ModelMapper mapper;
  @Override
  public void createRoom(CreateRoomRequestDto roomRequestDto, int quantity) {
    LinkedList<Room> rooms = new LinkedList<>();
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    
    // Persist room in room_table
    String patternRoomNameQuery = "%"
        .concat(roomRequestDto.getName())
        .concat("%");
    long totalExistingName = roomRepository.countByNameLikeAndUserId(patternRoomNameQuery, user.getId());
    for (int i = 1; i <= quantity; i++) {
      Room room = mapper.map(roomRequestDto, Room.class);
      room.setUser(user);
      // Pattern name is name plus 1 the total name
      room.setName(room.getName() + " " + ++totalExistingName);
      room.setStatus(RoomStatus.RoomAvailable);
      rooms.add(room);
    }
    List<Room> persistedRooms = roomRepository.saveAllAndFlush(rooms);

    // Persist default expense for room
    List<RoomExpense> roomExpenses = new ArrayList<>();
    List<Expense> expenses = expenseRepository.findAllByDefaultFlagIsTrueAndUserId(user.getId());
    expenses.forEach(expense -> {
      persistedRooms.forEach(persistedRoom -> {
        RoomExpense roomExpense = new RoomExpense();
        roomExpense.setRoom(persistedRoom);
        roomExpense.setExpense(expense);
        roomExpense.setOverridePriceFlag(false);
        roomExpenses.add(roomExpense);
      });
    });
    roomExpenseRepository.saveAll(roomExpenses);
  }

  @Override
  public void updateRoom(CreateRoomRequestDto roomRequestDto, long roomId) {
    Room room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);
    room.setName(roomRequestDto.getName());
    room.setStatus(RoomStatus.valueOf(roomRequestDto.getStatus()));
    roomRepository.save(room);
  }

  @Override
  public void deleteRoom(long roomId) {
    // Remove record in expense_room table if existing room_id
    List<RoomExpense> roomExpenses = roomExpenseRepository.findAllByRoomId(roomId);
    roomExpenses.forEach(this::removeRoomExpenseRecord);

    // Change status room in room table
    Room room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);
    room.setStatus(RoomStatus.Inactive);
    roomRepository.save(room);
  }

  private void removeRoomExpenseRecord(RoomExpense roomExpense) {
    roomExpenseRepository.delete(roomExpense);
  }
}
