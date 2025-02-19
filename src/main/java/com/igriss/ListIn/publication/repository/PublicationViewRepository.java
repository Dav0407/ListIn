package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationView;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PublicationViewRepository extends JpaRepository<PublicationView, UUID> {
    boolean existsByPublication_IdAndUser_UserId(UUID publicationId, UUID userUserId);

    boolean existsByPublicationAndUser(Publication publication, User user);

    @Modifying
    @Query(value = """
        INSERT INTO publication_views (id, publication_id, user_id, count, first_viewed_at, last_viewed_at)
        VALUES (:id, :publicationId, :userId, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        ON CONFLICT ON CONSTRAINT exception
        DO UPDATE SET
            count = publication_views.count + 1,
            last_viewed_at = CURRENT_TIMESTAMP
        """, nativeQuery = true)
    void upsertView(UUID id, UUID publicationId, UUID userId);

    Long countAllByPublication_IdAndUser_UserId(UUID publicationId, UUID userUserId);

    Long countAllByPublication_Id(UUID publicationId);
}
