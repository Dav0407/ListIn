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
@Table(name = "publication_statuses")
public class PublicationStatus{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "publication_status_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

}
