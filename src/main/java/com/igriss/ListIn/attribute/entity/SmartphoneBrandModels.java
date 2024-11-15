package com.igriss.ListIn.attribute.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "smartphone_brand_models")
public class SmartphoneBrandModels {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brand_model_id")
    private UUID id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeKey;
}
