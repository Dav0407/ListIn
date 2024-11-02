package com.igriss.ListIn.publication.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publication_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String imageName;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
