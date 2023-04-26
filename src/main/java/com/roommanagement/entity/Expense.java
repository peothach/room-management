package com.roommanagement.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "expense")
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "expense_id")
  private Integer id;
  @Column
  private String name;
  @Column(name = "unit_price_flag")
  private Boolean  unitPriceFlag;
  @Column
  private Double price;
  @Column
  private Boolean applyAllFlag;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_price_id")
  private UnitPrice unitPrice;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
