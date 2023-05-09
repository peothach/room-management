package com.roommanagement.dto.request.bill;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BillRequest {
  private LocalDate createDate;
  private Set<Integer> roomIds;
  private boolean applyAll;
}
