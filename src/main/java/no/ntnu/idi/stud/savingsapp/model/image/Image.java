package no.ntnu.idi.stud.savingsapp.model.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents an image.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id")
  private Long id;

  @NonNull
  @Column(name = "name", nullable = false)
  private String name;

  @Lob
  @Column(name = "data", length = Integer.MAX_VALUE)
  private byte[] data;
}
