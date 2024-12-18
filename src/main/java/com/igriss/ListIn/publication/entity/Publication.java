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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "publications")
@EntityListeners(AuditingEntityListener.class)
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Boolean bargain;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "condition_id")
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
