package com.roommanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "unit_price")
public class UnitPrice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "unit_price_id")
  private Integer id;

  @Column(name = "unit")
  private String unit;

  @OneToMany(mappedBy = "unitPrice")
  private List<Expense> expenses;
}
