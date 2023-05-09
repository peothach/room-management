package com.roommanagement.service.bill;

import com.roommanagement.dto.request.bill.BillRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.entity.*;
import com.roommanagement.repository.bill.BillRepository;
import com.roommanagement.repository.billdetail.BillDetailRepository;
import com.roommanagement.repository.expense.ExpenseRepository;
import com.roommanagement.repository.room.RoomRepository;
import com.roommanagement.repository.roomexpense.RoomExpenseRepository;
import com.roommanagement.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.roommanagement.valueoject.RoomStatus.CurrentlyRenting;

@Service
@RequiredArgsConstructor
public class CommandBillServiceImpl implements CommandBillService {
  private static final String EXPENSE = "Chi phí";
  private static final String SPACE_SYMBOL = " ";
  private static final String STATUS_DEFAULT = "Chưa thanh toán";
  private static final double DEFAULT_NUMBER = 0;
  private final RoomRepository roomRepository;
  private final RoomExpenseRepository roomExpenseRepository;
  private final ExpenseRepository expenseRepository;
  private final BillRepository billRepository;
  private final BillDetailRepository billDetailRepository;
  private final UserUtils userUtils;

  @Override
  public BaseResponseDto<?> createBill(BillRequest billRequest) {
    // Create bill record
    List<Bill> bills = createBills(billRequest);
    List<Bill> persistedBills = billRepository.saveAllAndFlush(bills);

    // Create bill_detail record
    persistedBills.forEach(bill -> {
      List<BillDetail> billDetails = createBillDetails(billRequest, bill.getId(), bill.getRoom());
      billDetailRepository.saveAll(billDetails);
    });

    return new BaseResponseDto<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
  }

  private List<Bill> createBills(BillRequest billRequest) {
    List<Room> rooms;
    List<Bill> bills = new LinkedList<>();
    LocalDate createDate = billRequest.getCreateDate();
    String billName = new StringBuilder()
        .append(EXPENSE)
        .append(SPACE_SYMBOL)
        .append(createDate)
        .toString();

    if (billRequest.isApplyAll()) {
      User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
      rooms = roomRepository.findAllByStatusAndUserId(CurrentlyRenting, user.getId());
    } else {
      Set<Integer> roomIds = billRequest.getRoomIds();
      rooms = roomRepository.findAllByIdInAndStatus(roomIds, CurrentlyRenting);
    }
    rooms.forEach(room -> {
      Bill bill = new Bill(billName, createDate, room, STATUS_DEFAULT);
      bills.add(bill);
    });

    return bills;
  }
  private double getPrice(int roomId) {

    return 0;
  }

  private List<BillDetail> createBillDetails(BillRequest billRequest, int billId, Room room) {
    double price;
    double oldNumber;


    List<BillDetail> billDetails = new LinkedList<>();
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    List<RoomExpense> roomExpenses = roomExpenseRepository.findAllByRoom(room);


    for (RoomExpense roomExpense: roomExpenses) {
      Optional<BillDetail> billDetailOptional = billDetailRepository
          .findFirstByRoomIdAndExpenseIdOrderByCreateDateDesc(roomExpense.getRoom().getId(), roomExpense.getExpense().getId());

      oldNumber = billDetailOptional.map(BillDetail::getNewNumber).orElse(DEFAULT_NUMBER);

      if (roomExpense.getOverridePriceFlag()) {
        price = roomExpense.getPrice();
      } else {
        Expense expense = expenseRepository
            .findById(roomExpense.getExpense().getId())
            .orElseThrow(RuntimeException::new);
        price = expense.getPrice();
      }

      BillDetail billDetail = new BillDetail();
      billDetail.setBill(billRepository.findById(billId).orElseThrow(RuntimeException::new));
      if (roomExpense.getExpense().isUnitPriceFlag()) {
        billDetail.setOldNumber(oldNumber);
        billDetail.setNewNumber(DEFAULT_NUMBER);
        billDetail.setQuantity(DEFAULT_NUMBER);
      }
      billDetail.setPrice(price);
      billDetail.setRoom(roomExpense.getRoom());
      billDetail.setExpense(roomExpense.getExpense());
      billDetail.setCreateDate(billRequest.getCreateDate());

      billDetails.add(billDetail);
    }
    return billDetails;
  }
}
