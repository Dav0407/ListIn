package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationLike;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationLikeRepository extends JpaRepository<PublicationLike, UUID> {

    Page<PublicationLike> findAllByUser(User user, Pageable pageable);

    List<PublicationLike> findAllByUser(User user);

    Boolean existsByUserAndPublication(User user, Publication publication);

    Optional<PublicationLike> findByPublication_IdAndUser_UserId(UUID publicationId, UUID userUserId);

    Boolean existsByPublication_IdAndUser_UserId(UUID publicationId, UUID userId);

    @Modifying
    @Query("DELETE FROM PublicationLike pl WHERE pl.publication.id = :publicationId")
    void deleteByPublicationId(@Param("publicationId") UUID publicationId);

    boolean existsByUser_UserIdAndPublication_Id(UUID userUserId, UUID publicationId);

}
