package com.igriss.ListIn.user.entity;

import com.igriss.ListIn.publication.entity.Publication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id")
    private UUID id;

    private Float rating;

    private String comment;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publicationId;
}
