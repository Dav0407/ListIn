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
@Table(name = "publication_images")
public class PublicationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    private String imageName;

    private String imageUrl;

    @Column(name = "is_primary")
    private Boolean isPrimaryImage;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
