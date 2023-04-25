package com.roommanagement.dto.request.expense;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseUpdateRequest {
  private String name;
  private PaymentMethod paymentMethod;
  private List<Long> roomIds;
  private boolean applyAllRooms;

  @Data
  public static class PaymentMethod{
    private Double price;
    private Integer unitPriceId;
    private Boolean isUnitPrice;
  }
}
