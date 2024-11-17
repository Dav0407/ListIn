package com.igriss.ListIn.attribute.entity;

import com.igriss.ListIn.publication.entity.static_entity.Category;
import jakarta.persistence.*;
import lombok.*;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category_attributes")
public class CategoryAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="category_attribute_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeKey attributeKey;
}
