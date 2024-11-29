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
@Table(name = "attribute_keys")
public class AttributeKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String helperText;

    @Column(nullable = false)
    private String dataType;
}
