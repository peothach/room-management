package com.roommanagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bill_id")
  private Integer id;
  private String name;
  private LocalDate createDate;
  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;
  private String status;

  public Bill(String name, LocalDate createDate, Room room, String status) {
    this.name = name;
    this.createDate = createDate;
    this.room = room;
    this.status = status;
  }
}
