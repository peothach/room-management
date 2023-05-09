package com.roommanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "bill_detail")
public class BillDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bill_detail_id")
  private Integer id;
  private LocalDate createDate;
  @ManyToOne
  @JoinColumn(name = "bill_id")
  private Bill bill;
  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;
  @ManyToOne
  @JoinColumn(name = "expense_id")
  private Expense expense;
  private Double oldNumber;
  private Double newNumber;
  private Double quantity;
  private double price;
}
