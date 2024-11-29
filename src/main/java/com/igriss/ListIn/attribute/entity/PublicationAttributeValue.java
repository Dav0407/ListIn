package com.igriss.ListIn.attribute.entity;

import com.igriss.ListIn.publication.entity.Publication;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Publication publication;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryAttribute categoryAttribute;

}
