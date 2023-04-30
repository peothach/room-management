package com.roommanagement.entity;

import com.roommanagement.valueoject.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "room_id")
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column
  private RoomStatus status;
  @Column
  private String description;
  @Column
  private String name;
  @OneToMany(mappedBy = "room")
  private List<Lodger> lodgers;
}
