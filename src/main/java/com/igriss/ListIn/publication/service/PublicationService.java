package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface PublicationService {
    UUID savePublication(PublicationRequestDTO request, Authentication connectedUser);

    PageResponse<UserPublicationDTO> findAllByUser(int page, int size, Authentication connectedUser);

    PageResponse<PublicationResponseDTO> findAllLatestPublications(int page, int size);

    PageResponse<PublicationResponseDTO> findAllByUserId(UUID userId, Integer page, Integer size);

    PublicationResponseDTO updateUserPublication(UUID publicationId, UpdatePublicationRequestDTO updatePublication);
}
