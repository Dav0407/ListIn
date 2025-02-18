package com.igriss.ListIn.publication.entity;

import com.igriss.ListIn.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publication_views", uniqueConstraints =
    @UniqueConstraint(name = "exception", columnNames = {"publication_id", "user_id"}
))
@EntityListeners(AuditingEntityListener.class)
public class PublicationView {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @Builder.Default
    private Long count = 1L;

    @LastModifiedDate
    @Column(nullable = false)
    private Date lastViewedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date firstViewedAt;
}