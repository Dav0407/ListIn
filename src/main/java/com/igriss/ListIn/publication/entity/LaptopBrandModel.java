package com.igriss.ListIn.publication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "laptop_brand_models")
public class LaptopBrandModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "laptop_brand_model_id")
    private UUID id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeKey;
}
