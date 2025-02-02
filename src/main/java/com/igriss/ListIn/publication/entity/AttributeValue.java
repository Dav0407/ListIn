package com.igriss.ListIn.publication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "attribute_values")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_value_id")
    private UUID id;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false, name = "value_uz")
    private String valueUz;

    @Column(nullable = false, name = "value_ru")
    private String valueRu;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AttributeValue parentValue;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeKey attributeKey;
}
