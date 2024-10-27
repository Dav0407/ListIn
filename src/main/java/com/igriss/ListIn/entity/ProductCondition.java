package com.igriss.ListIn.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_conditions")
public class ProductCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "condition_id")
    private UUID id;

    @Column(name = "condition_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
