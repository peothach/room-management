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
  private Boolean  unitPriceFlag;
  @Column
  private Double price;
  @Column
  private Boolean applyAllFlag;
  @Column
  private Boolean defaultFlag;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_price_id")
  private UnitPrice unitPrice;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Expense(String name, Double price, Boolean applyAllFlag, Boolean defaultFlag, Boolean unitPriceFlag, UnitPrice unitPrice) {
    this.name = name;
    this.price = price;
    this.applyAllFlag = applyAllFlag;
    this.defaultFlag = defaultFlag;
    this.unitPriceFlag = unitPriceFlag;
    this.unitPrice = unitPrice;
  }
}
