package com.igriss.ListIn.publication.entity.brand_models;

import com.igriss.ListIn.publication.entity.AttributeValue;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "laptop_processor_models")
public class LaptopProcessorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "laptop_processor_model_id")
    private UUID id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeValue attributeValue;
}

