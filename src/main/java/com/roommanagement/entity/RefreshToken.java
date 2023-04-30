package com.roommanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "refresh_token_id")
  private Integer id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;
}
