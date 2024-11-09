package com.igriss.ListIn.publication.entity;

import com.igriss.ListIn.attribute.entity.CategoryAttribute;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publication_attribute_values")
public class PublicationAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "publication_attribute_value_id")
    private UUID id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "category_attribute_id")
    private CategoryAttribute categoryAttribute;

}
