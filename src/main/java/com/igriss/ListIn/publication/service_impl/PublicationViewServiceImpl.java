package com.igriss.ListIn.publication.service_impl;

import com.igriss.ListIn.publication.repository.PublicationViewRepository;
import com.igriss.ListIn.publication.service.PublicationViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PublicationViewServiceImpl implements PublicationViewService {

    private final PublicationViewRepository publicationViewRepository;

    @Override
    public UUID view(UUID publicationId, UUID userId) {
        UUID viewId = UUID.randomUUID();
        publicationViewRepository.upsertView(viewId, publicationId, userId);
        return publicationId;
    }

    @Override
    public Long views(UUID publicationId) {
        return publicationViewRepository.countAllByPublication_Id(publicationId);
    }

    @Override
    public Boolean isViewed(UUID userId, UUID publicationId) {
        return publicationViewRepository.existsByUser_UserIdAndPublication_Id(userId, publicationId);
    }

    @Override
    public void deletePublicationViews(UUID publicationId) {
        publicationViewRepository.deleteAllByPublication_Id(publicationId);
    }
}
