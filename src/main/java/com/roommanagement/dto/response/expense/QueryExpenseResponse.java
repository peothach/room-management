package com.roommanagement.dto.response.expense;

import lombok.Data;

import java.util.List;

@Data
public class QueryExpenseResponse {
  private Integer id;
  private String name;
  private Double price;
  private boolean unitPriceFlag;
  private String unit;
  private List<Room> rooms;

  @Data
  public static class Room {
    private Integer id;
    private String name;
  }

  @Data
  public static class QueryExpense {
    private Integer expenseId;
    private String expenseName;
    private Double price;
    private boolean unitPriceFlag;
    private String unit;
    private Integer roomId;
    private String roomName;
  }

}
