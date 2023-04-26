package com.roommanagement.service.expense;

import com.roommanagement.dto.request.expense.ExpenseCreateRequest;
import com.roommanagement.dto.request.expense.ExpenseUpdateRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.entity.Expense;
import com.roommanagement.entity.Room;
import com.roommanagement.entity.RoomExpense;
import com.roommanagement.entity.User;
import com.roommanagement.repository.expense.ExpenseRepository;
import com.roommanagement.repository.room.RoomRepository;
import com.roommanagement.repository.roomexpense.RoomExpenseRepository;
import com.roommanagement.repository.unitprice.UnitPriceRepository;
import com.roommanagement.repository.user.UserRepository;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.util.UserUtils;
import com.roommanagement.valueoject.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommandExpenseServiceImpl implements CommandExpenseService {
  private final ExpenseRepository expenseRepository;
  private final RoomExpenseRepository roomExpenseRepository;
  private final UnitPriceRepository unitPriceRepository;
  private final RoomRepository roomRepository;
  private final UserRepository userRepository;
  private final UserUtils userUtils;

  @Override
  @Transactional
  public BaseResponseDto<?> createExpense(ExpenseCreateRequest createRequest) {
    List<RoomExpense> roomExpenses = new ArrayList<>();
    ExpenseCreateRequest.PaymentMethod paymentMethod = createRequest.getPaymentMethod();

    // Get current User
    UserDetailsImpl currentUser = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    User user = userRepository.findById(currentUser.getId()).orElseThrow(RuntimeException::new);

    // Update expense table
    Expense expense = new Expense();
    expense.setName(createRequest.getName());
    expense.setPrice(paymentMethod.getPrice());
    expense.setUnitPriceFlag(false);
    expense.setApplyAllFlag(false);
    expense.setUser(user);
    if (paymentMethod.getIsUnitPrice()) {
      expense.setUnitPriceFlag(paymentMethod.getIsUnitPrice());
      expense.setUnitPrice(unitPriceRepository.findById(paymentMethod.getUnitPriceId()).get());
    }
    if (createRequest.isApplyAllRooms()) {
      expense.setApplyAllFlag(true);
    }

    Expense expensePersisted = expenseRepository.saveAndFlush(expense);

    if (createRequest.isApplyAllRooms()) {
      // Add all room for expense when select apply for all
      List<Room> rooms = roomRepository.findAllByStatusIsNotAndUserId(RoomStatus.Inactive, user.getId());
      rooms.forEach(room -> {
        RoomExpense roomExpense = new RoomExpense();
        roomExpense.setRoom(room);
        roomExpense.setExpense(expensePersisted);
        roomExpense.setOverridePriceFlag(false);
        roomExpenses.add(roomExpense);
      });
    } else {
      // Add all selected room
      createRequest.getRoomIds().forEach(id -> {
        RoomExpense roomExpense = new RoomExpense();
        roomExpense.setRoom(roomRepository.findById(id).get());
        roomExpense.setExpense(expensePersisted);
        roomExpense.setOverridePriceFlag(false);
        roomExpenses.add(roomExpense);
      });
    }

    roomExpenseRepository.saveAll(roomExpenses);

    return new BaseResponseDto<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
  }

  @Override
  @Transactional
  public BaseResponseDto<?> updateExpense(Integer expenseId, ExpenseUpdateRequest updateRequest) {
    // Get existing room_expense
    List<RoomExpense> roomExpenses = roomExpenseRepository.findAllByExpenseId(expenseId);
    ExpenseUpdateRequest.PaymentMethod paymentMethod = updateRequest.getPaymentMethod();

    // Get current User
    UserDetailsImpl currentUser = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    User user = userRepository.findById(currentUser.getId()).orElseThrow(RuntimeException::new);

    // Update expense table
    Expense expense = expenseRepository.findById(expenseId).orElseThrow(RuntimeException::new);
    expense.setName(updateRequest.getName());
    expense.setPrice(paymentMethod.getPrice());
    expense.setUnitPriceFlag(false);
    expense.setUser(user);
    expense.setApplyAllFlag(false);
    if (paymentMethod.getIsUnitPrice()) {
      expense.setUnitPriceFlag(true);
      expense.setUnitPrice(unitPriceRepository.findById(paymentMethod.getUnitPriceId()).get());
    }
    if (updateRequest.isApplyAllRooms()) {
      expense.setApplyAllFlag(true);
    }
    Expense expensePersisted = expenseRepository.saveAndFlush(expense);

    if (updateRequest.isApplyAllRooms()) {
      // Add all room for expense when select apply for all
      List<Room> rooms = roomRepository.findAllByStatusIsNotAndUserId(RoomStatus.Inactive, user.getId());
      rooms.forEach(room -> {
        RoomExpense roomExpense = new RoomExpense();
        // Check if record is existing in DB
        Optional<RoomExpense> existingRoomExpense = roomExpenses
            .stream()
            .filter(i -> Objects.equals(i.getExpense().getId(), expenseId) && Objects.equals(i.getRoom().getId(), room.getId()))
            .findFirst();

        existingRoomExpense.ifPresent(item -> roomExpense.setId(item.getId()));
        roomExpense.setRoom(room);
        roomExpense.setExpense(expensePersisted);
        roomExpense.setOverridePriceFlag(false);

        roomExpenses.add(roomExpense);
        roomExpenseRepository.saveAll(roomExpenses);

      });
    } else {
      // Add all selected room
      updateRequest.getRoomIds().forEach(id -> {
        RoomExpense roomExpense = new RoomExpense();
        Optional<RoomExpense> existingRoomExpense = roomExpenses
            .stream()
            .filter(i -> Objects.equals(i.getExpense().getId(), expenseId) && Objects.equals(i.getRoom().getId(), id))
            .findFirst();
        Room room = roomRepository.findById(id).orElseThrow(RuntimeException::new);

        existingRoomExpense.ifPresent(item -> roomExpense.setId(item.getId()));
        roomExpense.setRoom(room);
        roomExpense.setExpense(expensePersisted);
        roomExpense.setOverridePriceFlag(false);
        roomExpenses.add(roomExpense);
      });

      roomExpenseRepository.saveAll(roomExpenses);


      // Delete room record in room_expense when user un-select existing record
      List<RoomExpense> roomExpensesNeedRemove = roomExpenseRepository.findAllByExpenseId(expenseId);
      List<Long> roomsForUpdate =  updateRequest.getRoomIds();
      for (int i = 0; i < roomExpensesNeedRemove.size(); i++) {
        for (int j = 0; j < roomsForUpdate.size(); j++) {
          if (Objects.equals(roomExpensesNeedRemove.get(i).getRoom().getId(), roomsForUpdate.get(j))) {
            roomExpensesNeedRemove.remove(i);
          }
        }
      }
      roomExpenseRepository.deleteAll(roomExpensesNeedRemove);
    }

//    roomExpenseRepository.saveAll(roomExpenses);

    return new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
  }

  @Override
  public BaseResponseDto<?> deactivateExpense(Integer expenseId) {
    return null;
  }
}
