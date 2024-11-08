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
@Table(name = "attribute_values")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_value_id")
    private UUID id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeKey attributeKey;
}
