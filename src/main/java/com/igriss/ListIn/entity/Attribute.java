package com.igriss.ListIn.entity;


import com.igriss.ListIn.entity.types.AttributeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.Columns;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "attribute")
@Entity
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private AttributeType type;

    @ElementCollection
    @Column(nullable = false)
    private List<String> options;
}
