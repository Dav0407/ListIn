package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface PublicationService {
    UUID savePublication(PublicationRequestDTO request, Authentication connectedUser);

    List<PublicationResponseDTO> findAllByUser(Authentication connectedUser);
}
