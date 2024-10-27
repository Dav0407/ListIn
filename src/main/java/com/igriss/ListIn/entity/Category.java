package com.igriss.ListIn.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private UUID id;
    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<Category> parentId;
}
