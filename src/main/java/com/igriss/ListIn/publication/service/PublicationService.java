package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.dto.PublicationNode;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface PublicationService {
    UUID savePublication(PublicationRequestDTO request, Authentication connectedUser);

    PageResponse<UserPublicationDTO> findAllByUser(int page, int size, Authentication connectedUser);

    List<PublicationNode> findAllLatestPublications(int page, int size);

    List<PublicationNode> findWithParentCategory(UUID parentCategoryId, Integer page, Integer size);

    PageResponse<PublicationResponseDTO> findAllByUserId(UUID userId, Integer page, Integer size);

    PublicationResponseDTO updateUserPublication(UUID publicationId, UpdatePublicationRequestDTO updatePublication);

    PageResponse<PublicationResponseDTO> findPublicationsContainingVideo( int page, int size);
}
