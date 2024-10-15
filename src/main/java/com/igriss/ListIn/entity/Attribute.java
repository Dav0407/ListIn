package com.igriss.ListIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private String type;

    @ElementCollection
    @CollectionTable(name = "attribute_options", joinColumns = @JoinColumn(name = "attribute_id"))
    @Column(name = "options")
    private List<String> options;
}
