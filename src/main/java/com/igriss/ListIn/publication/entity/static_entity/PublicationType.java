package com.igriss.ListIn.publication.entity.static_entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publication_types")
public class PublicationType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "publication_type_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
