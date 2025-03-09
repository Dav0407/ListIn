package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationLike;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface PublicationLikeService {
    void deletePublicationLikes(UUID publicationId);

    UUID like(User user, Publication publication);

    UUID unlike(UUID publicationId, UUID userId);

    Page<PublicationLike> getLikedPublications(User user, PageRequest of);

    Boolean isLiked(UUID userId, UUID publicationId);
}
