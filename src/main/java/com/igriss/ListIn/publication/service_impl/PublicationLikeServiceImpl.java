package com.igriss.ListIn.publication.service_impl;


import com.igriss.ListIn.exceptions.BadRequestException;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationLike;
import com.igriss.ListIn.publication.repository.PublicationLikeRepository;
import com.igriss.ListIn.publication.service.PublicationLikeService;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationLikeServiceImpl implements PublicationLikeService {

    private final PublicationLikeRepository publicationLikeRepository;

    @Override
    public UUID like(User user, Publication publication) {
        if (publicationLikeRepository.existsByUserAndPublication(user, publication)) {
            log.error("User have already liked to publication with id: {}", publication.getId());
            throw new BadRequestException(String.format("You have already liked to publication with id: %s", publication.getId()));
        }

        publicationLikeRepository.save(PublicationLike.builder()
                .user(user)
                .publication(publication)
                .build());
        return publication.getId();
    }

    @Override
    public UUID unlike(UUID publicationId, UUID userId) {

        if (!publicationLikeRepository.existsByUser_UserIdAndPublication_Id(userId, publicationId)) {
            log.error("User have not liked to this publication before: {}", publicationId);
            throw new BadRequestException(String.format("You have not liked to this publication before: %s", publicationId));
        }

        publicationLikeRepository.findByPublication_IdAndUser_UserId(publicationId, userId)
                .ifPresent(publicationLike -> publicationLikeRepository.deleteById(publicationLike.getId()));

        return publicationId;
    }

    @Override
    public Page<PublicationLike> getLikedPublications(User user, PageRequest of) {
        return publicationLikeRepository.findAllByUser(user, of);
    }

    @Override
    public Boolean isLiked(UUID userId, UUID publicationId){
        return publicationLikeRepository.existsByPublication_IdAndUser_UserId(publicationId, userId);
    }

    @Override
    public void deletePublicationLikes(UUID publicationId) {
        publicationLikeRepository.deleteByPublicationId(publicationId);
    }
}
