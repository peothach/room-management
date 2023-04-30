package com.roommanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Lodger")
public class Lodger {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lodger_id")
  private Integer id;
  private String name;
  private String email;
  private String phoneNumber;
  @OneToMany(mappedBy = "lodger")
  private List<Image> images;
  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;
  private boolean active;
}
