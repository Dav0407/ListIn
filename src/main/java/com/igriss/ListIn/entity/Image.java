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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl; // The URL or file path of the image

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Publication post; // Image associated with a post

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // Image associated with a user profile picture

    // Additional metadata can be added here
}
