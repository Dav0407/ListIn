package com.igriss.ListIn.attribute.entity;

import com.igriss.ListIn.publication.entity.Publication;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "attribute_key")
public class AttributeKey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publicationId;
}
