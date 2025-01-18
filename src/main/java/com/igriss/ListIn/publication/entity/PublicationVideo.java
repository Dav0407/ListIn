package com.igriss.ListIn.publication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publication_videos")
@ToString
public class PublicationVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID videoId;

    private String videoName;

    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
