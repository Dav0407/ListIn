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

    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
}
