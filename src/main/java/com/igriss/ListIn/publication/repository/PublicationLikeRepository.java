package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationLike;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationLikeRepository extends JpaRepository<PublicationLike, UUID> {

    Page<PublicationLike> findAllByUser(User user, Pageable pageable);

    List<PublicationLike> findAllByUser(User user);

    Boolean existsByUserAndPublication(User user, Publication publication);

    void deleteByPublication(Publication publication);

    Optional<PublicationLike> findByPublication_IdAndUser_UserId(UUID publicationId, UUID userUserId);
}
