package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import org.springframework.security.core.Authentication;

public interface PublicationService {
    void savePublication(PublicationRequestDTO request, Authentication connectedUser);
}
