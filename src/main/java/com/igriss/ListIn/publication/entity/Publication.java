package com.igriss.ListIn.publication.entity;

import com.igriss.ListIn.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Category> categoryId;

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
