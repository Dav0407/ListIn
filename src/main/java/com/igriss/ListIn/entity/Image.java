package com.igriss.ListIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String imageUrl; // The URL or file path of the image

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
