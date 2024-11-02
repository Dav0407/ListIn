package com.igriss.ListIn.publication.entity;

import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.entity.static_entity.ProductCondition;
import com.igriss.ListIn.publication.entity.static_entity.PublicationStatus;
import com.igriss.ListIn.publication.entity.static_entity.PublicationType;
import com.igriss.ListIn.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable=false)
    private Integer stockQuantity;

    @OneToMany(mappedBy = "publication")
    private List<ProductImage> images;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name =  "condition_id")
    private ProductCondition productCondition;

    @ManyToOne
    @JoinColumn(name = "publication_type_id")
    private PublicationType publicationType;

    @ManyToOne
    @JoinColumn(name = "publication_status_id")
    private PublicationStatus publicationStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

}
