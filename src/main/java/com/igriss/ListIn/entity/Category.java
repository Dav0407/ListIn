package com.igriss.ListIn.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "category")
@Entity
public class Category {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false)
        private String name;

        @Column(name = "previewText",nullable = false)
        private String previewText;

        @Column(name = "imageResId",nullable = false)
        private String imageResId;

        @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        private List<Subcategory> subcategories;
}
