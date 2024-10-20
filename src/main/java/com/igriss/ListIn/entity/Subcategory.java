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
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String previewText;
    private String imagePath;  // Assuming images are stored as URLs or identifiers

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
