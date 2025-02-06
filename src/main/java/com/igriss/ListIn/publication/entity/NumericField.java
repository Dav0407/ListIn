package com.igriss.ListIn.publication.entity;

import com.igriss.ListIn.publication.entity.static_entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "numeric_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumericField {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "field_id")
    private UUID id;

    @Column(nullable = false)
    private String fieldName;

    @Column(nullable = false)
    private String fieldNameUz;

    @Column(nullable = false)
    private String fieldNameRu;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String description;

    private String descriptionUz;

    private String descriptionRu;
}
