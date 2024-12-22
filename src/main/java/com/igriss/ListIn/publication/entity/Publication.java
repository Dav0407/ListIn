package com.igriss.ListIn.publication.entity;

import com.igriss.ListIn.publication.entity.static_entity.Category;

import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.publication.enums.PublicationStatus;
import com.igriss.ListIn.publication.enums.PublicationType;
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
    @Column(name = "publication_id")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Boolean bargain;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    private PublicationType publicationType;

    private PublicationStatus publicationStatus;

    private ProductCondition productCondition;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime datePosted;

    @LastModifiedDate
    private LocalDateTime dateUpdated;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

}
