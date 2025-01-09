package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.UserPublicationDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface PublicationService {
    UUID savePublication(PublicationRequestDTO request, Authentication connectedUser);

    PageResponse<UserPublicationDTO> findAllByUser(int page, int size, Authentication connectedUser);
}
