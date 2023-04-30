package com.roommanagement.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "room_expense", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"room_id", "expense_id"})}
)
public class RoomExpense {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_expense_id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "expense_id")
  private Expense expense;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

  @Column(name = "price")
  private Double price;

  @Column(name = "override_price_flag")
  private Boolean overridePriceFlag;
}