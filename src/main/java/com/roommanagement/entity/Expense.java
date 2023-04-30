package com.roommanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@Table(name = "expense")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "expense_id")
  private Integer id;
  @Column
  private String name;
  @Column(name = "unit_price_flag")
  private boolean  unitPriceFlag;
  @Column
  private Double price;
  @Column
  private boolean applyAllFlag;
  @Column
  private boolean defaultFlag;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_price_id")
  private UnitPrice unitPrice;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
