package com.igriss.ListIn.entity;

import jakarta.persistence.*;
import lombok.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

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
