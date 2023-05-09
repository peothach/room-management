package com.roommanagement.dto.response.billdetail;

import lombok.Data;

import java.util.List;

@Data
public class BillDetailResponse {
  private int roomId;
  private String name;
  private List<ExpenseQuery> expenses;
  @Data
  public static class ExpenseQuery {
    private int id;
    private String name;
    private Double price;
    private String unit;
    private Double oldNumber;
    private Double newNumber;
    private Double quantity;
  }
}
