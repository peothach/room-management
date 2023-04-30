package com.roommanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "image")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id")
  private Integer id;
  private String title;
  private String description;
  private String imagePath;
  private String imageFileName;
  private String url;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lodger_id")
  private Lodger lodger;
}
