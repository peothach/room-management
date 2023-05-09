package com.roommanagement.repository.billdetail;

import com.roommanagement.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
  Optional<BillDetail> findFirstByRoomIdAndExpenseIdOrderByCreateDateDesc(int roomId, int expenseId);
}
