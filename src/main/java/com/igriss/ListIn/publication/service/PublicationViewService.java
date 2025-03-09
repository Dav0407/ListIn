package com.igriss.ListIn.publication.service;

import java.util.UUID;

public interface PublicationViewService {
    UUID view(UUID publicationId, UUID userId);

    Long views(UUID id);

    Boolean isViewed(UUID userId, UUID publicationId);

    void deletePublicationViews(UUID publicationId);
}
