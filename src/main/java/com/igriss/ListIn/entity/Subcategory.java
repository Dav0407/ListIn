package com.igriss.ListIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;
    private String name;
    private String previewText;
    private String imageResId;  // Assuming images are stored as URLs or identifiers

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_id")
    private List<Attribute> attributes;
}
