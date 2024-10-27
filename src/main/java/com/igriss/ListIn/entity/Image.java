package com.igriss.ListIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publication_images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl; // The URL or file path of the image

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publicationId; // Image associated with a post

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user; // Image associated with a user profile picture

    @Column(nullable = false)
    private Boolean isPrimary;
    // Additional metadata can be added here
}
