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
@Table(name = "subcategory")
@Entity
public class Subcategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String previewText;

    @Column(nullable = false)
    private String imageResId;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Attribute> attributes;
}
