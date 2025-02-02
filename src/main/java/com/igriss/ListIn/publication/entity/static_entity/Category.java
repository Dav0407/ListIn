package com.igriss.ListIn.publication.entity.static_entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_name_uz")
    private String nameUz;

    @Column(name = "category_name_ru")
    private String nameRu;

    private String description;

    private String descriptionUz;

    private String descriptionRu;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
}
