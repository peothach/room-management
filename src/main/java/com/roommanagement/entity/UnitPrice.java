package com.roommanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "unit_price")
@NoArgsConstructor
public class UnitPrice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "unit_price_id")
  private Integer id;

  @Column(name = "unit")
  private String unit;

  public UnitPrice(String unit) {
    this.unit = unit;
  }
}
